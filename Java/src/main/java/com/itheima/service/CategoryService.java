package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> get();

    Category findById(Integer id);

    void update(Category category);
}
