package com.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class User {

    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
