package com.puff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.puff.entity.User;
import com.puff.exception.ShopException;
import com.puff.form.UserForm;
import com.puff.result.ResponseEnum;
import com.puff.service.AdminService;
import com.puff.service.UserService;
import com.puff.util.JwtUtil;
import com.puff.util.MD5Util;
import com.puff.utils.RegexValidateUtil;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.ResultVO;
import com.puff.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author puff
 * @since 2023-06-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResultVO register(@RequestBody UserForm userForm){
        boolean b = RegexValidateUtil.checkMobile(userForm.getMobile());
        if(!b){
            throw new ShopException(ResponseEnum.MOBILE_ERROR.getMsg());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", userForm.getMobile());
        User one = this.userService.getOne(queryWrapper);
        if(one!=null){
            throw new ShopException(ResponseEnum.MOBILE_EXIST.getMsg());
        }
        //验证码校验
//        String code = (String)this.redisTemplate.opsForValue().get("uushop-sms-code-" + userForm.getMobile());
//        if(!code.equals(userForm.getCode())){
//            throw new ShopException(ResponseEnum.SMS_CODE_ERROR.getMsg());
//        }
        User user = new User();
        user.setMobile(userForm.getMobile());
        user.setPassword(MD5Util.getSaltMD5(userForm.getPassword()));
        this.userService.save(user);
        return ResultVOUtil.success("注册成功");
    }

    @GetMapping("/login")
    public ResultVO login(UserForm userForm){
        boolean b = RegexValidateUtil.checkMobile(userForm.getMobile());
        if(!b){
            throw new ShopException(ResponseEnum.MOBILE_ERROR.getMsg());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", userForm.getMobile());
        User one = this.userService.getOne(queryWrapper);
        if(one == null){
            throw new ShopException(ResponseEnum.ACCOUNT_ERROR.getMsg());
        }
        //验证密码
        if (!MD5Util.getSaltverifyMD5(userForm.getPassword(),one.getPassword())) {
            throw new ShopException(ResponseEnum.ACCOUNT_ERROR.getMsg());
        }
        //生成Token
        String token = JwtUtil.createToken(one.getUserId(), one.getMobile());
        UserVO userVO = new UserVO(one.getUserId(), one.getMobile(), one.getPassword(), token);
        return ResultVOUtil.success(userVO);
    }

    @GetMapping("/checkToken/{token}")
    public ResultVO checkToken(@PathVariable("token") String token){
        boolean b = JwtUtil.checkToken(token);
        if(!b){
            throw new ShopException(ResponseEnum.TOKEN_ERROR.getMsg());
        }
        return ResultVOUtil.success(null);
    }

}

