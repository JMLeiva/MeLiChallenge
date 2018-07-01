package com.jml.melichallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.repository.persistence.MeLiDatabase;
import com.jml.melichallenge.repository.persistence.SearchTerm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchTermlRepository
{
	// Simple in memoryCache
	Map<String, List<SearchTerm>> memoryCache;
	private MeLiDatabase meLiDatabase;

	@Inject
	public SearchTermlRepository(MeLiDatabase meLiDatabase)
	{
		memoryCache = new HashMap<>();
		this.meLiDatabase = meLiDatabase;
	}

	public LiveData<List<SearchTerm>> getTerms(final String qStr)
	{
		final MediatorLiveData<List<SearchTerm>> data = new MediatorLiveData<>();

		List<SearchTerm> memItem = memoryCache.get(qStr);

		if(memItem != null)
		{
			data.setValue(memItem);
		}




		data.addSource(meLiDatabase.searchTermDao().findLike(qStr), new Observer<List<SearchTerm>>()
		{
			@Override
			public void onChanged(@Nullable List<SearchTerm> searchTerms)
			{
				data.postValue(searchTerms);
			}
		});

		return data;
	}

	public void addNew(String qStr)
	{
		meLiDatabase.searchTermDao().insert(new SearchTerm(qStr));
		memoryCache.get(qStr).add(new SearchTerm(qStr));
	}
}
