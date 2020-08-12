package com.jhipster.common.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import feign.QueryMap;

/**
 * 此注解没什么实际作用，只是用于标记，便于后期替换<br/>
 * 在spring-cloud-openfeign升级到2.1.0.RC1版本后由官方的@SpringQueryMap替换<br/>
 *
 * @author yuenke
 * @date 2019/9/11 13:38
 * @see feign.QueryMap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SpringQueryMap {

    /**
     * Alias for {@link #encoded()}.
     *
     * @see QueryMap#encoded()
     */
    @AliasFor("encoded")
    boolean value() default false;

    /**
     * Specifies whether parameter names and values are already encoded.
     *
     * @see QueryMap#encoded()
     */
    @AliasFor("value")
    boolean encoded() default false;
}
