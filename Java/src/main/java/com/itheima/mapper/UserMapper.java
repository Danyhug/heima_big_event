package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    public User findByUsername(String username);

    @Insert("INSERT INTO user(username, password, create_time, update_time) values (#{username}, #{password}, now(), now())")
    public void add(String username, String password);

    @Update("UPDATE user SET nickname=#{nickname}, email=#{email},update_time=#{updateTime} where id = #{id}")
    void update(User user);

    @Update("UPDATE user SET user_pic=#{url},update_time=now() where id = #{id}")
    void updateAvatar(String url, Integer id);

    @Update("UPDATE user SET password=#{md5String},update_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
