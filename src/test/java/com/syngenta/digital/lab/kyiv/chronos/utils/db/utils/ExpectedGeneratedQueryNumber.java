package com.syngenta.digital.lab.kyiv.chronos.utils.db.utils;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExpectedGeneratedQueryNumber {
    QueryType queryType();

    long expectedNumber();
}
