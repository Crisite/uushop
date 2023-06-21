package com.puff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVO {
    private Integer adminId;
    private String username;
    private String password;
    private String imgUrl;
    private String name;
    private String token;
}
