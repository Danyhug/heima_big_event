package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 判断用户名是否重复
        User user = userService.findByUsername(username);
        if (user == null) {
            // 注册
            userService.register(username, password);
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 判断用户名是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }

        // 检查密码是否一致
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", username);
            // 登录成功，
            String jwt = JwtUtil.genToken(claims);

            // 把token存在redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            // token存放一小时
            operations.set(jwt, jwt, 1, TimeUnit.HOURS);

            return Result.success(jwt);
        } else {
            return Result.error("密码错误");
        }
    }

    // 获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        // 根据用户名查询用户信息
        HashMap<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    // 更新用户数据
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // 更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    // 更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, Object> params, @RequestHeader("Authorization") String token) {
        // 校验参数
        String oldPwd = (String) params.get("old_pwd");
        String newPwd = (String) params.get("new_pwd");
        String rePwd = (String) params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数有误");
        }

        // 原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        // 登录用户信息
        User loginUser = userService.findByUsername(username);
        if (!Md5Util.getMD5String(oldPwd).equals(loginUser.getPassword())) {
            return Result.error("原密码错误");
        }

        // 新密码是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("新密码不一致");
        }

        // 更新密码
        userService.updatePwd(Md5Util.getMD5String(newPwd), loginUser.getId());

        // 密码修改成功后，更改redis的token，使旧令牌失效
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
}
