package com.tt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {

    private Integer id;

//    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

//    @Email(message = "Email không đúng định dạng")
    private String email;

//    @NotNull(message = "Mật khẩu không được để trống")
//    @Min(value = 8, message = "Mật khẩu phải có ít nhất 8 kí tự")
    private String password;
}
