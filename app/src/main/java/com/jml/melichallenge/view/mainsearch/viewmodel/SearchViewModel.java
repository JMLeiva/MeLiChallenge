package com.jml.melichallenge.view.mainsearch.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.repository.SearchRepository;

public class SearchViewModel extends AndroidViewModel
{
	private final MutableLiveData<SearchQuery> searchQueryInput = new MutableLiveData();

	private LiveData<SearchResult> searchObservable;
	private MutableLiveData<RequestState> requestStateObservable;
	private MutableLiveData<ErrorWrapper> errorWrapperObservable;

	SearchQuery query;

	public SearchViewModel(Application application)
	{
		super(application);


		requestStateObservable = new MutableLiveData<>();
		errorWrapperObservable = new MutableLiveData<>();

		searchObservable = Transformations.switchMap(searchQueryInput, new Function<SearchQuery, LiveData<SearchResult>>()
		{
			@Override
			public LiveData<SearchResult> apply(SearchQuery query)
			{
				requestStateObservable.setValue(RequestState.LOADING);

				return Transformations.map(SearchRepository.getInstance().search(query), new Function<ResponseWrapper<SearchResult>, SearchResult>()
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

	public LiveData<SearchResult> getSearchObservable()
	{
		return searchObservable;
	}

	public LiveData<RequestState> getStateObservable()
	{
		return requestStateObservable;
	}

	public LiveData<ErrorWrapper> getErrorObservable()
	{
		return errorWrapperObservable;
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

	@Override
	protected void onCleared()
	{
		searchObservable = null;
	}
}
