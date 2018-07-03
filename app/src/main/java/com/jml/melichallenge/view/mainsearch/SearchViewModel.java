package com.jml.melichallenge.view.mainsearch;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.repository.ItemRepository;
import com.jml.melichallenge.view.common.BaseViewModel;

import javax.inject.Inject;

public class SearchViewModel extends BaseViewModel<SearchResult>
{
	private MutableLiveData<SearchQuery> searchQueryInput;
	private ItemRepository itemRepository;
	private SearchQuery query;
	private SearchResult currentResults;

	@Inject
	SearchViewModel(Application application, ItemRepository itemRepository)
	{
		super(application);
		this.itemRepository = itemRepository;
	}

	@Override
	protected LiveData<SearchResult> createDataObservable()
	{
		searchQueryInput = new MutableLiveData<>();
		return Transformations.switchMap(searchQueryInput, new Function<SearchQuery, LiveData<SearchResult>>()
		{
			@Override
			public LiveData<SearchResult> apply(SearchQuery query)
			{
				requestStateObservable.setValue(RequestState.LOADING);
				return getTransformationLiveData(query);
			}
		});
	}

	private LiveData<SearchResult> getTransformationLiveData(SearchQuery query)
	{
		return Transformations.map(itemRepository.search(query), new Function<ResponseWrapper<SearchResult>, SearchResult>()
		{
			@Override
			public SearchResult apply(ResponseWrapper<SearchResult> input)
			{
				if(input.isSuccessfull())
				{
					currentResults = input.getData();
					requestStateObservable.setValue(RequestState.SUCCESS);
					return input.getData();
				}
				else
				{
					errorWrapperObservable.setValue(input.getError());
					requestStateObservable.setValue(RequestState.FAILURE);
					return null;
				}
			}
		});
	}

	public SearchResult getCurrentSearchResult()
	{
		return currentResults;
	}

	public void setQuery(SearchQuery query)
	{
		currentResults = null;
		this.query = query;
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

		this.searchQueryInput.setValue(query);
		this.query.advancePage();
	}

	public LiveData<SearchQuery> getQuery()
	{
		return this.searchQueryInput;
	}
}
