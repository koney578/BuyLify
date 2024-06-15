package com.buylify.buylifyapp.authentication;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String oldPassword;
    private String newPassword;
}
