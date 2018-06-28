package com.jml.melichallenge.repository;


public class ResponseWrapper<T>
{
	private T data;
	private ErrorWrapper errorWrapper;

	public boolean isSuccessfull()
	{
		return errorWrapper == null;
	}

	public T getData()
	{
		if(!isSuccessfull())
		{
			throw new IllegalStateException("Can't retrieve data from a non successfull repsonse");
		}

		return data;
	}

	public ErrorWrapper getError()
	{
		if(isSuccessfull())
		{
			throw new IllegalStateException("Can't retrieve error from a successfull repsonse");
		}

		return errorWrapper;
	}

	public static<T> ResponseWrapper<T> successfullResponse(T data)
	{
		ResponseWrapper result = new ResponseWrapper();
		result.data = data;
		return result;
	}

	public static <T> ResponseWrapper<T> errorResponse(int code, String message)
	{
		ResponseWrapper result = new ResponseWrapper();
		result.errorWrapper = new ErrorWrapper(code, message);
		return result;
	}
}
