package com.jml.melichallenge.view.mainsearch;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.model.states.EntityListState;
import com.jml.melichallenge.network.ConnectionManager;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.repository.ItemRepository;
import com.jml.melichallenge.view.common.EntityListViewModel;

import javax.inject.Inject;

/*
 *	ViewModel used to perform search
 *  It allows pagination, region configuration and sorting,
 * */
public class SearchViewModel extends EntityListViewModel<SearchResult>
{
	private MutableLiveData<SearchQuery> searchQueryInput;
	private ItemRepository itemRepository;
	private SearchQuery query;
	private SearchResult currentResults;

	@Inject
	ConnectionManager connectionManager;

	@Inject
	SearchViewModel(Application application, ItemRepository itemRepository)
	{
		super(application);
		this.itemRepository = itemRepository;
		query = new SearchQuery.Builder().pageSize(SearchQuery.DEFAILT_PAGE_SIZE).site(SearchQuery.DEFAULT_SITE).build();
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

	public void setQueryStr(String qStr)
	{
		currentResults = null;
		this.query.setQStr(qStr);
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

	public void setSorting(String sorting)
	{
		this.query.setSort(sorting);
		query.resetPaging();
		this.searchQueryInput.setValue(query);
	}


	public void setSite(String site)
	{
		this.query.setSite(site);
		query.resetPaging();

		if(query.getQStr() != null && !query.getQStr().isEmpty())
		{
			this.searchQueryInput.setValue(query);
		}
	}

	@Override
	protected EntityListState stateForResult(SearchResult input)
	{
		if(input != null)
		{
			if(input.resultsCount() > 0)
			{
				return EntityListState.SUCCESSFULL;
			}
			else
			{
				return EntityListState.NO_RESULTS;
			}
		}
		else
		{
			if(!connectionManager.isInternetConnected())
			{
				return EntityListState.NO_CONNECTION;
			}

			return EntityListState.ERROR;
		}
	}
}
