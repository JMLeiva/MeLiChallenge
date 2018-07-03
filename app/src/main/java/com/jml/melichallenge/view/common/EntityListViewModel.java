package com.jml.melichallenge.view.common;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.states.EntityListState;

public abstract class EntityListViewModel<T> extends BaseViewModel<T>
{
	protected LiveData<EntityListState> entityListStateMutableLiveData;

	public EntityListViewModel(Application application)
	{
		super(application);
		entityListStateMutableLiveData = Transformations.map(dataObservable, new Function<T, EntityListState>()
		{
			@Override
			public EntityListState apply(T input)
			{
				return stateForResult(input);
			}
		});
	}

	protected abstract EntityListState stateForResult(T input);

	public LiveData<EntityListState> getEntityListStateObserbale() {return entityListStateMutableLiveData; }
}