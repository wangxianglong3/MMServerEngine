package com.mm.engine.framework.control.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2015/11/16.
 * 外部进来的访问
 * 任何一个opcode在整个应用中只有一个处理方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {
    /**
     * opcode,框架根据该参数进行路径导航
     * **/
    short opcode();
}
