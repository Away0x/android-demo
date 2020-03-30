package com.away0x.lib_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface FragmentDestination {

    String pageUrl();

    /**
     * 是否需要登录后跳转
     */
    boolean needLogin() default false;

    /**
     * 是否为首页
     */
    boolean asStarter() default false;

}
