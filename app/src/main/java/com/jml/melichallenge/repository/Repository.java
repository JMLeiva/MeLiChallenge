package com.jml.melichallenge.repository;

import com.jml.melichallenge.network.MeLiApi;

public abstract class Repository
{
	private MeLiApi api;

	public Repository(MeLiApi meLiApi)
	{
		this.api = meLiApi;
	}

	public MeLiApi getApi()
	{
		return api;
	}
}
