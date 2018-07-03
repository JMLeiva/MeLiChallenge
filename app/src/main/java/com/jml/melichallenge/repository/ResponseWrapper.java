package com.jml.melichallenge.repository;


public class ResponseWrapper<T>
{
	private T data;
	private ErrorWrapper errorWrapper;
	private boolean successful;

	public ResponseWrapper()
	{
		successful = false;
	}

	public boolean isSuccessfull()
	{
		return successful;
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
		ResponseWrapper<T> result = new ResponseWrapper<>();
		result.data = data;
		result.successful = true;
		return result;
	}

	public static <T> ResponseWrapper<T> errorResponse(int code, String message)
	{
		ResponseWrapper<T> result = new ResponseWrapper<>();
		result.errorWrapper = new ErrorWrapper(code, message);
		result.successful = false;
		return result;
	}
}
