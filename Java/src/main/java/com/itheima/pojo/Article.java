package com.itheima.pojo;


import com.itheima.anno.State;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;//主键ID
    @NotNull
    private String title;//文章标题
    @NotNull
    private String content;//文章内容
    @URL
    private String coverImg;//封面图像
    @State
    private String state;//发布状态 已发布|草稿
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
