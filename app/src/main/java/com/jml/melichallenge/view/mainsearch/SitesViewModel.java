package com.jml.melichallenge.view.mainsearch;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.states.EntityListState;
import com.jml.melichallenge.network.ConnectionManager;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.repository.SiteRepository;
import com.jml.melichallenge.view.common.EntityListViewModel;

import java.util.List;

import javax.inject.Inject;

public class SitesViewModel extends EntityListViewModel<List<IdName>>
{
	private SiteRepository siteRepository;
	private MutableLiveData<Void> triggerLiveData;

	@Inject
	ConnectionManager connectionManager;

	@Inject
	SitesViewModel(Application application, SiteRepository siteRepository)
	{
		super(application);
		this.siteRepository = siteRepository;
	}

	@Override
	protected LiveData<List<IdName>> createDataObservable()
	{
		triggerLiveData = new MutableLiveData<>();
		return Transformations.switchMap(triggerLiveData, new Function<Void, LiveData<List<IdName>>>()
		{
			@Override
			public LiveData<List<IdName>> apply(Void v)
			{
				requestStateObservable.setValue(RequestState.LOADING);
				return getTransformationLiveData();
			}
		});
	}

	private LiveData<List<IdName>> getTransformationLiveData()
	{
		return Transformations.map(siteRepository.getSites(), new Function<ResponseWrapper<? extends List<IdName>>, List<IdName>>()
		{
			@Override
			public List<IdName> apply(ResponseWrapper<? extends List<IdName>> input)
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

	public void get()
	{
		triggerLiveData.setValue(null);
	}


	@Override
	protected EntityListState stateForResult(List<IdName> input)
	{
		if(input != null)
		{
			if(!input.isEmpty())
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
