package com.missfresh.common.validation;

import java.lang.annotation.*;

/**
 * <pre>
 * 自动表单验注解
 * </pre>
 * <small> 2018/9/4 12:00 | caigl@missfresh.cn</small>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ValidForm {
}
