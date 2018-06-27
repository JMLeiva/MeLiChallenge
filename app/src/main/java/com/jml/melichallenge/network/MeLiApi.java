package com.jml.melichallenge.network;

import com.jml.melichallenge.model.SearchResult;

import java.io.IOException;

public interface MeLiApi
{
	class ApiResponseException extends Exception
	{
		int errorCode;

		public ApiResponseException(int errorCode, String message)
		{
			super(message);
			this.errorCode = errorCode;
		}

		public int getCode()
		{
			return errorCode;
		}
	}

	void search(String siteId, String qStr, int offset, int limit, ApiCallback<SearchResult> callback);
}
