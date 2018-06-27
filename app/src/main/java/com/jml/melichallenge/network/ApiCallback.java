package com.jml.melichallenge.network;

public interface ApiCallback<T>
{
	void onResponse(T response);
	void onFailure(Throwable t);
}