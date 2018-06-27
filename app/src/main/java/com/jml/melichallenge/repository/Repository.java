package com.jml.melichallenge.repository;

import com.jml.melichallenge.network.MeLiApi;

public abstract class Repository<T>
{
	private ActionHandler<T> actionHandler;
	private MeLiApi api;

	public Repository(MeLiApi meLiApi)
	{
		actionHandler = new ActionHandler<>();
		this.api = meLiApi;
	}

	public MeLiApi getApi()
	{
		return api;
	}

	public ActionHandler<T> getActionHandler()
	{
		return actionHandler;
	}
}
