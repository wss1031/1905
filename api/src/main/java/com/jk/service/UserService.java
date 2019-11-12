package com.jk.service;

import com.jk.model.TreeModel;
import com.jk.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> queryUser();

    void addUser(UserModel user);

    UserModel queryUserById(Integer userId);

    void delAll(String[] userId);

    void addUSers(List<UserModel> list);

    List<String> queryPowerByUserid(Integer userId);

    List<TreeModel> queryTree(Integer userId);

/*    List<TreeModel> queryTree();*/

    UserModel findUserByName(String username);

}
