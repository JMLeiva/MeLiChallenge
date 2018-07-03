package com.jml.melichallenge.view.common;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.repository.ErrorWrapper;

public abstract class BaseViewModel<T> extends AndroidViewModel
{
	private final LiveData<T> dataObservable = createDataObservable();

	protected MutableLiveData<RequestState> requestStateObservable;
	protected MutableLiveData<ErrorWrapper> errorWrapperObservable;

	public BaseViewModel(Application application)
	{
		super(application);
		requestStateObservable = new MutableLiveData<>();
		errorWrapperObservable = new MutableLiveData<>();
	}

	protected abstract LiveData<T> createDataObservable();

	public LiveData<T> getDataObservable()
	{
		return dataObservable;
	}

	public LiveData<RequestState> getStateObservable()
	{
		return requestStateObservable;
	}

	public LiveData<ErrorWrapper> getErrorObservable()
	{
		return errorWrapperObservable;
	}

}