package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,cover_img,content,state,create_time,update_time,category_id,create_user) values(#{title},#{coverImg},#{content},#{state},#{createTime},#{updateTime},#{categoryId},#{createUser})")
    void insert(Article article);

    /**
     * 分页查询文章列表
     * @param userId
     * @param categoryId
     * @param state
     * @return
     */
    List<Article> list(Integer userId, Integer categoryId, String state);
    
    @Select("SELECT * FROM article WHERE id = #{id} and create_user = #{userId}")
    Article selectById(Integer userId, Integer id);

    @Delete("DELETE FROM article WHERE id = #{id} and create_user = #{userId}")
    void delete(Integer userId, Integer article);

    @Update("UPDATE article SET title=#{title},cover_img=#{coverImg},content=#{content},state=#{state},update_time=#{updateTime},category_id=#{categoryId},create_user=#{createUser} WHERE id = #{id}")
    void update(Article article);
}
