package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.network.ApiCallback;
import com.jml.melichallenge.network.MeLiApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SiteRepository extends Repository
{
	// Simple in memoryCache
	private List<IdName> memoryCache;

	@Inject
	public SiteRepository(MeLiApi api)
	{
		super(api);
		memoryCache = new ArrayList<>();
	}


	public LiveData<ResponseWrapper<? extends List<IdName>>> getSites()
	{
		final MutableLiveData<ResponseWrapper<? extends List<IdName>>> data = new MutableLiveData<>();

		if(!memoryCache.isEmpty())
		{
			// ONLY CACHE IF AVAILABLE POLICY
			data.setValue(ResponseWrapper.successfullResponse(new ArrayList<>(memoryCache)));
		}
		else
		{
			getApi().getSites(new ApiCallback<List<IdName>>()
			{
				@Override
				public void onResponse(List<IdName> response)
				{
					updateMemoryCache(response);
					data.setValue(ResponseWrapper.successfullResponse(response));
				}

				@Override
				public void onFailure(int code, String message)
				{
					data.setValue(ResponseWrapper.<List<IdName>>errorResponse(code, message));
				}
			});
		}

		return data;
	}

	private void updateMemoryCache(List<IdName> data)
	{
		memoryCache.clear();
		memoryCache.addAll(data);
	}
}
