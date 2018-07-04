package com.jml.melichallenge.view.mainsearch;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.repository.SearchTermRepository;
import com.jml.melichallenge.repository.persistence.SearchTerm;
import com.jml.melichallenge.utils.BackgroundRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 *	ViewModel used to obtain search suggestions
 * */
public class SearchTermViewModel extends AndroidViewModel
{
	private final LiveData<List<String>> dataObservable = createDataObservable();
	private MutableLiveData<String> searchQueryInput;
	private SearchTermRepository searchTermRepository;
	private BackgroundRunner backgroundRunner;

	@Inject
	SearchTermViewModel(Application application, SearchTermRepository searchTermRepository, BackgroundRunner backgroundRunner)
	{
		super(application);
		this.searchTermRepository = searchTermRepository;
		this.backgroundRunner = backgroundRunner;
	}

	private LiveData<List<String>> createDataObservable()
	{
		searchQueryInput = new MutableLiveData<>();
		return Transformations.switchMap(searchQueryInput, new Function<String, LiveData<List<String>>>()
		{
			@Override
			public LiveData<List<String>> apply(String query)
			{
				return getTransformationLiveData(query);
			}
		});
	}

	private LiveData<List<String>> getTransformationLiveData(String query)
	{
		return Transformations.map(searchTermRepository.getTerms(query), new Function<List<SearchTerm>, List<String>>()
		{
			@Override
			public List<String> apply(List<SearchTerm> input)
			{
				List<String> result = new ArrayList<>();
				for(SearchTerm searchTerm : input)
				{
					result.add(searchTerm.getTerm());
				}
				return result;
			}
		});
	}

	public void addNewTerm(final String qStr)
	{
		backgroundRunner.run(new Runnable()
		{
			@Override
			public void run()
			{
				searchTermRepository.addNew(qStr);
			}
		});
	}

	public LiveData<List<String>> getDataObservable()
	{
		return dataObservable;
	}

	public void setQuery(String query)
	{
		this.searchQueryInput.setValue(query);
	}
}
