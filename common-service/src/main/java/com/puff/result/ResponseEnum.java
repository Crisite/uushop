package com.puff.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ResponseEnum {

    STOCK_ERROR(300,"库存不足"),
    MOBILE_ERROR(301,"手机号格式错误"),
    MOBILE_EXIST(302,"手机号已注册"),
    MOBILE_IS_NULL(303,"手机号未注册"),
    PASSWORD_ERROR(304,"密码错误"),
    TOKEN_ERROR(305,"Token失效"),
    SMS_SEND_ERROR(306,"短信发送失败"),
    SMS_CODE_ERROR(307,"验证码错误"),
    ACCOUNT_ERROR(308,"帐号或密码错误");

    private Integer code;
    private String msg;

}
