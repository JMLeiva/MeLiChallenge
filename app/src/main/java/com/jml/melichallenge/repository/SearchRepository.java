package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;
import com.jml.melichallenge.network.MeLiApiImpl;

public class SearchRepository extends Repository<SearchResult>
{
	private static SearchRepository _instance;

	public static synchronized SearchRepository getInstance()
	{
		if(_instance == null)
		{
			_instance = new SearchRepository(new MeLiApiImpl());
		}

		return _instance;
	}


	private SearchRepository(MeLiApi api)
	{
		super(api);
	}

	public void search(String siteId, String qStr)
	{
		getApi().search(siteId, qStr, new ApiCallback<SearchResult>()
		{
			@Override
			public void onResponse(SearchResult response)
			{
				getActionHandler().sendGet(response);
			}

			@Override
			public void onFailure(Throwable t)
			{
				getActionHandler().sendError();
			}
		});
	}
}
