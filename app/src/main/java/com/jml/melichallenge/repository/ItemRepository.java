package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemRepository extends Repository
{
	// Simple in memoryCache
	Map<String, Item> memoryCache;

	@Inject
	public ItemRepository(MeLiApi api)
	{
		super(api);
		memoryCache = new HashMap<>();
	}

	public LiveData<ResponseWrapper<SearchResult>> search(SearchQuery searchQuery)
	{
		final MutableLiveData<ResponseWrapper<SearchResult>> data = new MutableLiveData<>();

		getApi().search(searchQuery.getSite(), searchQuery.getQStr(), searchQuery.getOffset(), searchQuery.getLimit(),  new ApiCallback<SearchResult>()
		{
			@Override
			public void onResponse(SearchResult response)
			{
				updateMemoryCache(response.getResults());
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

		Item memItem = memoryCache.get(itemId);

		if(memItem != null)
		{
			data.setValue(ResponseWrapper.successfullResponse(memItem));
		}

		getApi().getItem(itemId,  new ApiCallback<Item>()
		{
			@Override
			public void onResponse(Item response)
			{
				updateMemoryCache(response);
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

	private void updateMemoryCache(List<Item> items)
	{
		for(Item item : items)
		{
			updateMemoryCache(item);
		}
	}

	private void updateMemoryCache(Item item)
	{
		memoryCache.put(item.getId(), item);
	}
}
