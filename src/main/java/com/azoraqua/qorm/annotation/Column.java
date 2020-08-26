package com.azoraqua.qorm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.JDBCType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {

    String name() default "";

    JDBCType type() default JDBCType.NULL;

    boolean primary() default false;

    boolean auto() default false;

    boolean nullable() default false;
}
