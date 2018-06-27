package com.jml.melichallenge.repository;


public class RepositoryObserver
{
	public interface Error
	{
		void onError();
	}

	public interface Get<T>
	{
		void onGet(T object);
	}
}
