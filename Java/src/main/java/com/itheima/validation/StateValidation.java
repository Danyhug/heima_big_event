package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * state校验规则
 * < 给哪个注解提供校验规则，校验的数据类型 >
 */
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        // 是这两个说明通过
        return s.equals("草稿") || s.equals("已发布");
    }
}
