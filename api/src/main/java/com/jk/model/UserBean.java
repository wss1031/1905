package com.jk.model;

import lombok.Data;

import java.io.Serializable;

/**
 * author：wdd
 * create time:2019/10/12
 * email：
 * tel：
 */
@Data
public class UserBean implements Serializable {

    private Integer id;
    private String name;
    private String password;
    //业务字段
    private String roleName;
    private String roleid;//1,2,3
}
