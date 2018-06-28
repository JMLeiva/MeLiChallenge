package com.jml.melichallenge.network;

public interface ApiCallback<T>
{
	void onResponse(T response);
	void onFailure(int code, String message);
}