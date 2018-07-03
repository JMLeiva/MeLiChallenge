package com.jml.melichallenge.repository;

import com.jml.melichallenge.network.MeLiApi;

abstract class Repository
{
	private MeLiApi api;

	Repository(MeLiApi meLiApi)
	{
		this.api = meLiApi;
	}

	MeLiApi getApi()
	{
		return api;
	}
}
