package com.jk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {
    private Integer userId;
    private String username;
    private String userpwd;
    private String userbirthday;

    private String roleid;//1,2,3
}
