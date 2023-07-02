package com.puff.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.puff.entity.Admin;
import com.puff.exception.ShopException;
import com.puff.form.AdminForm;
import com.puff.result.ResponseEnum;
import com.puff.service.AdminService;
import com.puff.util.JwtUtil;
import com.puff.util.MD5Util;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.AdminVO;
import com.puff.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author puff
 * @since 2023-06-13
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody AdminForm adminForm) {
        System.out.println(adminForm.getUsername()+adminForm.getPassword());
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username",adminForm.getUsername());
        Admin admin = this.adminService.getOne(adminQueryWrapper);
        if (admin == null || !MD5Util.getSaltverifyMD5(adminForm.getPassword(), admin.getPassword())) {
            throw new ShopException(ResponseEnum.ACCOUNT_ERROR.getMsg());
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(admin, adminVO);
        adminVO.setToken(JwtUtil.createToken(admin.getAdminId(),admin.getUsername()));


        return ResultVOUtil.success(adminVO);
    }

    @GetMapping("/checkToken")
    public ResultVO checkToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        boolean b = JwtUtil.checkToken(token);
        if(!b) {
            throw new ShopException(ResponseEnum.TOKEN_ERROR.getMsg());
        }
        return ResultVOUtil.success(null);
    }
}

