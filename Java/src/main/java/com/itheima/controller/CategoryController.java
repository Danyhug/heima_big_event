package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 添加分类
    @PostMapping
    public Result add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }

    // 查看当前用户创建的文章分类
    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.get());
    }

    // 获取文章分类详情
    @GetMapping("/detail")
    public Result<Category> detail(@Param("id") Integer id) {
        return Result.success(categoryService.findById(id));
    }

    // 更新文章分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }
}
