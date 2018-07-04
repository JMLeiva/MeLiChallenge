package com.jml.melichallenge.network;

/* Callback for API responses */
public interface ApiCallback<T>
{
	void onResponse(T response);
	void onFailure(int code, String message);
}