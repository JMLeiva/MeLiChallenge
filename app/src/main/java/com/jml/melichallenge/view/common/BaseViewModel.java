package com.jml.melichallenge.view.common;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.repository.ErrorWrapper;

/*
 * Base view model bases in a model-state change usability.
 * It informs when there was a change of state.
 * When state is "SUCCESSFUL", you can retreive it's date with "getData()"
 *
 * It also privides information about the request-state, and errors.
 * */
public abstract class BaseViewModel<T> extends AndroidViewModel
{
	final LiveData<T> dataObservable = createDataObservable();

	protected MutableLiveData<RequestState> requestStateObservable;
	protected MutableLiveData<ErrorWrapper> errorWrapperObservable;

	private BlindObserver<T> blindObserver;

	BaseViewModel(Application application)
	{
		super(application);
		requestStateObservable = new MutableLiveData<>();
		errorWrapperObservable = new MutableLiveData<>();

		blindObserver = new BlindObserver<>();

		dataObservable.observeForever(blindObserver);
	}

	protected abstract LiveData<T> createDataObservable();

	public T getData()
	{
		return dataObservable.getValue();
	}

	public LiveData<RequestState> getStateObservable()
	{
		return requestStateObservable;
	}

	public LiveData<ErrorWrapper> getErrorObservable()
	{
		return errorWrapperObservable;
	}

	@Override
	public void onCleared()
	{
		super.onCleared();
		dataObservable.removeObserver(blindObserver);
	}

}