package com.mundane.examassistant.bean;

/**
 * @author : mundane
 * @time : 2017/4/12 20:32
 * @description :
 * @file : Result.java
 */

public class Result<T> {
	public int code;
	public String message;
	public T data;
}
