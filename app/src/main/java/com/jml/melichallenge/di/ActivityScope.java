package com.jml.melichallenge.di;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

import javax.inject.Scope;

@Scope @Retention(RetentionPolicy.RUNTIME) public @interface ActivityScope {
}