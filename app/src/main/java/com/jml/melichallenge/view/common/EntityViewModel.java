package com.jml.melichallenge.view.common;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.jml.melichallenge.model.states.EntityState;

/*
 *	BaseViewModel normal entities
 * */
public abstract class EntityViewModel<T> extends BaseViewModel<T>
{
	protected LiveData<EntityState> entityStateMutableLiveData;

	public EntityViewModel(Application application)
	{
		super(application);
		entityStateMutableLiveData = Transformations.map(dataObservable, new Function<T, EntityState>()
		{
			@Override
			public EntityState apply(T input)
			{
				return stateForResult(input);
			}
		});
	}

	protected abstract EntityState stateForResult(T input);

	public LiveData<EntityState> getEntityStateObserbale() {return entityStateMutableLiveData; }
}