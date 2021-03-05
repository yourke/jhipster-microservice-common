package com.jhipster.common.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 枚举值校验
 * 
 * @author yonker
 * @date 2021/3/5 12:37
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Exists.ExistsChecker.class})
public @interface Exists {
    String message() default "The string is invalid.";

    String[] list() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ExistsChecker implements ConstraintValidator<Exists, Object> {
        String[] list;

        @Override
        public void initialize(Exists constraintAnnotation) {
            list = constraintAnnotation.list();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            // 非空由其它注解去校验，此注解只校验正常情况
            if (value == null) {
                return true;
            }
            for (String str : list) {
                if (str.equals(value)) {
                    return true;
                }
            }
            return false;
        }
    }
}
