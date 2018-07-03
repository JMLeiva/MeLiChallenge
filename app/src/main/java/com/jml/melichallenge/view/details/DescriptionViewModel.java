package com.jml.melichallenge.view.details;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.repository.DescriptionRepository;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.view.common.BaseViewModel;

import javax.inject.Inject;

public class DescriptionViewModel extends BaseViewModel<Description>
{
	private MutableLiveData<String> itemIdInput;
	private DescriptionRepository descriptionRepository;

	@Inject
	DescriptionViewModel(Application application, DescriptionRepository descriptionRepository)
	{
		super(application);
		this.descriptionRepository = descriptionRepository;
	}

	@Override
	protected LiveData<Description> createDataObservable()
	{
		itemIdInput = new MutableLiveData<>();

		return Transformations.switchMap(itemIdInput, new Function<String, LiveData<Description>>()
		{
			@Override
			public LiveData<Description> apply(String itemId)
			{
				requestStateObservable.setValue(RequestState.LOADING);

				return getTransformationLiveData(itemId);
			}
		});
	}

	private LiveData<Description> getTransformationLiveData(String itemId)
	{
		return Transformations.map(descriptionRepository.getDescription(itemId), new Function<ResponseWrapper<Description>, Description>()
		{
			@Override
			public Description apply(ResponseWrapper<Description> input)
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

	public void setItemId(String itemId)
	{
		if(itemIdInput.getValue() != null && itemIdInput.getValue().equals(itemId))
		{
			return;
		}

		this.itemIdInput.setValue(itemId);
	}

	public void retry()
	{
		this.itemIdInput.setValue(this.itemIdInput.getValue());
	}
}
