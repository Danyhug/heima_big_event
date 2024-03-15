package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    // 用户名查找
    User findByUsername(String username);

    // 注册
    void register(String username, String password);

    // 更新
    void update(User user);

    // 更新头像
    void updateAvatar(String url);

    void updatePwd(String md5String, Integer id);
}
