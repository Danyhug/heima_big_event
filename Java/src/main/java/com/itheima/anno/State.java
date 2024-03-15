package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented // 元注解，说明该State可以被抽取到帮助文档中
// 元注解，说明该State可以被用在什么上面（类，方法。。）
@Target({ElementType.FIELD})
// 标识在哪个阶段会被保留
@Retention(RetentionPolicy.RUNTIME)
// 谁给注解定义规则
@Constraint(
        // 说明注解的定义规则
        validatedBy = {StateValidation.class}
)
public @interface State {
    // 提供校验失败后的提示信息
    String message() default "state参数的值只能是草稿 或 已发布";

    Class<?>[] groups() default {};

    // 负载，获取State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
