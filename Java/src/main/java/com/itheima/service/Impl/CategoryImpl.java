package com.itheima.service.Impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @param category
     */
    @Override
    public void add(Category category) {
        // 补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 获取用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> get() {
        // 获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return categoryMapper.selectAll(id);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
}
