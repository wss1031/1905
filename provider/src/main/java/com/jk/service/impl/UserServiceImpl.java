package com.jk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.mapper.UserDao;
import com.jk.model.TreeModel;
import com.jk.model.UserModel;
import com.jk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserModel> queryUser() {

        return userDao.queryUser();
    }

    @Override
    public void addUser(UserModel user) {
        Integer id = user.getUserId();
        if (id != null) {//修改
            //修改用户
            userDao.updateUser(user);
        } else {
            userDao.addUser(user);
        }
    }

    @Override
    public UserModel queryUserById(Integer userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public void delAll(String[] userId) {
        userDao.delAll(userId);
    }

    @Override
    public void addUSers(List<UserModel> list) {
        userDao.addUSers(list);
    }

    @Override
    public List<String> queryPowerByUserid(Integer userId) {
        return userDao.queryPowerByUserid(userId);
    }

  /*  @Override
    public List<TreeModel> queryTree() {
        List<TreeModel> list = new ArrayList<TreeModel>();

        int pid = -1;
        //查询一级节点
        //提取公共方法的快捷键：alt+shift+m
        list = queryNodes(pid);
        return list;
    }

    private List<TreeModel> queryNodes(Integer pid) {
        List<TreeModel> list = userDao.queryTreeByPidAndUserid(pid);
        for (TreeModel treeModel : list) {
            Integer id = treeModel.getId();
            //查询对应的子节点
            List<TreeModel> nodes = queryNodes(id);//递归：自己调自己
            treeModel.setChildren(nodes);
        }
        return list;
    }*/


    @Override
    public List<TreeModel> queryTree(Integer userid) {


        int pid =0;
        //查询一级节点
        //提取公共方法的快捷键：alt+shift+m
        List<TreeModel>list = queryNodes(pid,userid);
        return list;
    }

    private List<TreeModel> queryNodes(Integer pid,Integer userid) {
        List<TreeModel> list = userDao.queryTreeByPidAndUserid(pid,userid);
        for (TreeModel treeModel : list) {
            Integer id = treeModel.getId();
            //查询对应的子节点
            List<TreeModel> nodes = queryNodes(id,userid);//递归：自己调自己
            treeModel.setChildren(nodes);
        }
        return list;
    }

    @Override
    public UserModel findUserByName(String username) {

        return userDao.findUserByName(username);
    }


}