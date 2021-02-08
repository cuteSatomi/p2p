package com.zzx.p2p.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记登录的标签 @Target(ElementType.METHOD)表示作用在方法上，@Retention(RetentionPolicy.RUNTIME)表示保留到运行时
 *
 * @author zzx
 * @date 2021-02-08 13:36:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {
}
