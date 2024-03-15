package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("INSERT into category(category_name, category_alias, create_user, create_time, update_time) values (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);

    @Select("SELECT * from category WHERE create_user = #{id}")
    List<Category> selectAll(Integer id);

    @Select("SELECT * from category WHERE id = #{id}")
    Category selectById(Integer id);

    @Update("UPDATE category SET category_name=#{categoryName}, category_alias=#{categoryAlias}, update_time=now() where id = #{id}")
    void update(Category category);
}
