package com.jafir.testdagger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jafir on 2017/3/24.
 */


@Target({ElementType.METHOD,ElementType.FIELD})
@Documented
@Retention(RUNTIME)
public @interface Jafir {


    enum Gender {BOY,GIRL}

    Gender gender() default Gender.BOY;

}
