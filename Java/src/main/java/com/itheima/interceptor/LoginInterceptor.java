package com.itheima.interceptor;

import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

  // 登录拦截的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("Authorization");

            // 查看当前token和redis的token是否一致，一致放行
            String redisToken = stringRedisTemplate.opsForValue().get(token);
            if (redisToken != null && redisToken.equals(token)) {
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 保存数据到局部线程
                ThreadLocalUtil.set(claims);
                // 验证成功，放行
                return true;
            }
            throw new RuntimeException();
        } catch (Exception e) {
            // 401
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 结束接口请求时，清除数据，防止内存泄露
        ThreadLocalUtil.remove();
    }
}
