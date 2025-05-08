package com.erling.daoJ.user;

import com.erling.pojo.user.PassWord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PassWordMapper {

   @Select("SELECT * FROM password")
   List<PassWord>  selectAll();


   @Insert("INSERT INTO password (id, password, salt) VALUES (#{id}, #{password}, #{salt})")
   void addPassWord(int id, String password, String salt);

   @Select("SELECT * FROM password WHERE id = #{id}")
   PassWord selectById(int id);

}
