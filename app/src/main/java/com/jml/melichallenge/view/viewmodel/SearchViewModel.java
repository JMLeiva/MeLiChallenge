package com.jml.melichallenge.view.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.SearchRepository;

public class SearchViewModel extends AndroidViewModel
{
	private final MutableLiveData<SearchQuery> searchQueryInput = new MutableLiveData();
	private LiveData<SearchResult> searchObservable;

	SearchQuery query;

	public SearchViewModel(Application application)
	{
		super(application);

		searchObservable = Transformations.switchMap(searchQueryInput, new Function<SearchQuery, LiveData<SearchResult>>()
		{
			@Override
			public LiveData<SearchResult> apply(SearchQuery query)
			{
				return SearchRepository.getInstance().search(query);
			}
		});
	}

	public LiveData<SearchResult> getSearchObservable()
	{
		return searchObservable;
	}

	public void setQuery(SearchQuery query)
	{
		this.query = query;
		this.searchQueryInput.setValue(query);
	}

	public void resetPaging()
	{
		if(query == null)
		{
			return;
		}

		query.resetPaging();
		this.searchQueryInput.setValue(query);
	}

	public void advance()
	{
		if(query == null)
		{
			return;
		}

		if(query == null)
		{
			return;
		}

		this.query.advancePage();
		this.searchQueryInput.setValue(query);
	}

	@Override
	protected void onCleared()
	{
		searchObservable = null;
	}
}
