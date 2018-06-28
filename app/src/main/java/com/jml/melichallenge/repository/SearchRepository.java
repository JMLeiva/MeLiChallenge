package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;
import com.jml.melichallenge.network.MeLiApiImpl;

public class SearchRepository extends Repository
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

	public LiveData<ResponseWrapper<SearchResult>> search(SearchQuery searchQuery)
	{
		final MutableLiveData<ResponseWrapper<SearchResult>> data = new MutableLiveData<>();

		getApi().search(searchQuery.getSite(), searchQuery.getQStr(), searchQuery.getOffset(), searchQuery.getLimit(),  new ApiCallback<SearchResult>()
		{
			@Override
			public void onResponse(SearchResult response)
			{
				data.setValue(ResponseWrapper.successfullResponse(response));
			}

			@Override
			public void onFailure(int code, String message)
			{
				data.setValue(ResponseWrapper.<SearchResult>errorResponse(code, message));
			}
		});

		return data;
	}
}
