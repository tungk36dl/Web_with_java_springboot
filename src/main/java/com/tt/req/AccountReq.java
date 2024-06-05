package com.tt.req;

import lombok.Data;

import java.util.Date;

@Data
public class AccountReq {

    private String username;

    private String email;

    private String password;

    private Date createdAt;

    private Date updatedAt;
}
