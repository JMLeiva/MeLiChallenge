package com.jml.melichallenge.repository;

public class ErrorWrapper
{
	private int code;
	private String message;

	public ErrorWrapper(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public int getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}
}
