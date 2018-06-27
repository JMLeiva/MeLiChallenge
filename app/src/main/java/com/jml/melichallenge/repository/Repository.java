package com.jml.melichallenge.repository;

import com.jml.melichallenge.network.MeLiApi;

public abstract class Repository<T>
{
	private ObserverHandler<T> observerHandler;
	private MeLiApi api;

	public Repository(MeLiApi meLiApi)
	{
		observerHandler = new ObserverHandler<>();
		this.api = meLiApi;
	}

	public MeLiApi getApi()
	{
		return api;
	}

	public ObserverHandler<T> getObserverHandler()
	{
		return observerHandler;
	}
}
