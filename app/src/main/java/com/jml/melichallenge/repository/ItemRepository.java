package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;
import com.jml.melichallenge.network.MeLiApiImpl;

public class ItemRepository extends Repository
{
	private static ItemRepository _instance;

	public static synchronized ItemRepository getInstance()
	{
		if(_instance == null)
		{
			_instance = new ItemRepository(new MeLiApiImpl());
		}

		return _instance;
	}


	private ItemRepository(MeLiApi api)
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

	public LiveData<ResponseWrapper<Item>> getItem(String itemId)
	{
		final MutableLiveData<ResponseWrapper<Item>> data = new MutableLiveData<>();

		getApi().getItem(itemId,  new ApiCallback<Item>()
		{
			@Override
			public void onResponse(Item response)
			{
				data.setValue(ResponseWrapper.successfullResponse(response));
			}

			@Override
			public void onFailure(int code, String message)
			{
				data.setValue(ResponseWrapper.<Item>errorResponse(code, message));
			}
		});

		return data;
	}
}
