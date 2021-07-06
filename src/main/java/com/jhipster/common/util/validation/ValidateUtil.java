package com.jhipster.common.util.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 属性校验Util
 * 
 * @author yonker
 * @date 2021/7/6 10:13
 */
public class ValidateUtil {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    /**
     * 对象校验(为了避免大量的校验在前端堆积,影响用户体验，只返回一个错误提示)
     * 
     * @param bindResult
     *            BindingResult
     * @return 错误信息字符串
     */
    public static String validateResult(BindingResult bindResult) {
        List<ObjectError> allErrors = bindResult.getAllErrors();
        String errStrs = "";
        for (ObjectError error : allErrors) {
            String str = error.getDefaultMessage();
            if ("".equals(errStrs)) {
                errStrs += str;
            } else {
                break;
            }
        }
        return errStrs;
    }

    /**
     * 手动校验
     *
     * @param t
     *            校验对象
     * @return 错误信息集合
     */
    public static <T> List<String> validate(T t) {
        Validator validator = FACTORY.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    /**
     * 手动校验
     *
     * @param t
     *            校验对象
     * @param c
     *            所选分组
     * @return 错误信息集合
     */
    public static <T> List<String> validate(T t, Class c) {
        Validator validator = FACTORY.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, c);
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }
}
