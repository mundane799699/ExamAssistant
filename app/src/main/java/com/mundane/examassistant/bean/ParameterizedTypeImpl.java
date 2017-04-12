package com.mundane.examassistant.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : mundane
 * @time : 2017/4/12 20:29
 * @description :
 * @file : ParameterizedTypeImpl.java
 */

public class ParameterizedTypeImpl implements ParameterizedType {
	private final Class raw;
	private final Type[] args;
	public ParameterizedTypeImpl(Class raw, Type[] args) {
		this.raw = raw;
		this.args = args != null ? args : new Type[0];
	}
	@Override
	public Type[] getActualTypeArguments() {
		return args;
	}
	@Override
	public Type getRawType() {
		return raw;
	}
	@Override
	public Type getOwnerType() {return null;}
}
