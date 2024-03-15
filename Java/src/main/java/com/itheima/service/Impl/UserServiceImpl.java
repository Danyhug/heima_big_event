package com.itheima.service.Impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 使用username查看用户
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * @param username
     * @param password
     */
    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    /**
     * @param user
     */
    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    /**
     * @param url
     */
    @Override
    public void updateAvatar(String url) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(url, id);
    }

    /**
     * @param md5String
     * @param id
     */
    @Override
    public void updatePwd(String md5String, Integer id) {
        userMapper.updatePwd(md5String, id);
    }
}
