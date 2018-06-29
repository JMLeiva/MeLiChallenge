package com.jml.melichallenge.view.mainsearch.viewmodel;

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

public class SearchViewModel extends BaseViewModel<SearchResult>
{
	private MutableLiveData<SearchQuery> searchQueryInput;
	SearchQuery query;

	public SearchViewModel(Application application)
	{
		super(application);
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

				return Transformations.map(ItemRepository.getInstance().search(query), new Function<ResponseWrapper<SearchResult>, SearchResult>()
				{
					@Override
					public SearchResult apply(ResponseWrapper<SearchResult> input)
					{
						if(input.isSuccessfull())
						{
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
		});
	}

	public void setQuery(SearchQuery query)
	{
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
}
