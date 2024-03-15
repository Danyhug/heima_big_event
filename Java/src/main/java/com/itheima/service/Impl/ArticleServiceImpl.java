package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 新增文章
     *
     * @param article
     */
    @Override
    public void add(Article article) {
        // 获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateUser(id);
        articleMapper.insert(article);
    }

    /**
     * 查看所有文章
     *
     * @return
     */
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 创建pageBean对象
        PageBean<Article> articlePageBean = new PageBean<>();

        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);

        // 转为page对象，使其可以使用page的方法
        Page<Article> p = (Page<Article>) as;

        // 数据填充到pageBean对象中
        articlePageBean.setTotal(p.getTotal());
        articlePageBean.setItems(p.getResult());
        return articlePageBean;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Article detail(Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return articleMapper.selectById(userId, id);
    }

    /**
     * @param article
     */
    @Override
    public void delete(Integer article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        articleMapper.delete(userId, article);
    }

    /**
     * @param article
     */
    @Override
    public void update(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }
}
