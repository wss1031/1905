package com.jk.mapper;

import com.jk.model.TreeModel;
import com.jk.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Mapper
public interface UserDao {

    @Select("select * from t_user")
    List<UserModel> queryUser();
    @Insert("insert into t_user(username,userpwd,userbirthday) values(#{user.username},#{user.userpwd},#{user.userbirthday})")
    void addUser(@Param("user") UserModel user);

    @Select("select u.* from t_user u where u.userId=#{value} group by u.userId")
    UserModel queryUserById(Integer userId);

    @Update("update t_user u set u.username=#{user.username},u.userpwd=#{user.userpwd},u.userbirthday=#{user.userbirthday} where u.userId=#{user.userId}")
    void updateUser(@Param("user")UserModel user);

    void delAll(@Param("userId") String[] userId);

    void addUSers(List<UserModel> list);
     @Select("select t.premission from t_nav t\n" +
             "left join t_tree_role rp on t.id= rp.treeid\n" +
             "left join t_user_role  ur on rp.roleid = ur.roleid\n" +
             "where  ur.userid = #{value}")
    List<String> queryPowerByUserid(Integer userId);

   @Select("select n.* from t_nav n\n" +
           "left join t_tree_role rp on n.id = rp.treeid\n" +
           "left join t_user_role  ur on rp.roleid= ur.roleid where n.pid=#{pid} and ur.userid =#{userid} and n.fiag='mean'\n" +
           "group by n.id")
    List<TreeModel> queryTreeByPidAndUserid(@Param("pid") int pid, @Param("userid") int userid);
    @Select("select * from tt_user where username=#{value}")
    UserModel findUserByName(String username);
/*    @Select("select n.* from t_nav n\n" +
            "left join t_tree_role rp on n.id = rp.treeid\n" +
            "left join t_user_role  ur on rp.roleid= ur.roleid where n.pid=#{value} and n.fiag='mean'\n" +
            "group by n.id")
    List<TreeModel> queryTreeByPidAndUserid(Integer pid);*/
}