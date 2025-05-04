package com.erling.daoJ.user;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {



    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id = #{id}")
    User getUserById(int id);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Insert("insert into user(username) values( #{username})")
    void addUser(String username);

    @Select("select id from user where username = #{username}")
    int getID(String username);

}
