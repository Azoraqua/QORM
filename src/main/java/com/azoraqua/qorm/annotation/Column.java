package com.azoraqua.qorm.annotation;

import com.azoraqua.qorm.com.azoraqua.qorm.hasher.DefaultHasher;
import com.azoraqua.qorm.com.azoraqua.qorm.hasher.Hasher;

import java.lang.annotation.*;
import java.sql.JDBCType;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {

    String name() default "";

    JDBCType type() default JDBCType.NULL;

    boolean primary() default false;

    boolean auto() default false;

    boolean nullable() default false;

    Class<? extends Hasher> hasher() default DefaultHasher.class;
}
