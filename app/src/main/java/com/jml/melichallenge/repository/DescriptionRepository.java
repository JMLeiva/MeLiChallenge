package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
 * Repository used to retreive Items descriptions
 * */
@Singleton
public class DescriptionRepository extends Repository
{
	// Simple in memoryCache
	private Map<String, Description> memoryCache;

	@Inject
	public DescriptionRepository(MeLiApi api)
	{
		super(api);
		memoryCache = new HashMap<>();
	}


	public LiveData<ResponseWrapper<Description>> getDescription(final String itemId)
	{
		final MutableLiveData<ResponseWrapper<Description>> data = new MutableLiveData<>();

		Description memItem = memoryCache.get(itemId);

		if(memItem != null)
		{
			data.setValue(ResponseWrapper.successfullResponse(memItem));
		}

		getApi().getItemDescription(itemId,  new ApiCallback<Description>()
		{
			@Override
			public void onResponse(Description response)
			{
				updateMemoryCache(response, itemId);
				data.setValue(ResponseWrapper.successfullResponse(response));
			}

			@Override
			public void onFailure(int code, String message)
			{
				data.setValue(ResponseWrapper.<Description>errorResponse(code, message));
			}
		});

		return data;
	}

	private void updateMemoryCache(Description item, String itemId)
	{
		memoryCache.put(itemId, item);
	}
}
