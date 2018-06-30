package com.jml.melichallenge.view.mainsearch.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.repository.ItemRepository;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.view.common.BaseViewModel;

import javax.inject.Inject;

public class ItemViewModel extends BaseViewModel<Item>
{
	private final MutableLiveData<String> itemIdInput = new MutableLiveData();
	@Inject
	ItemRepository itemRepository;

	public ItemViewModel(Application application)
	{
		super(application);
	}

	@Override
	protected LiveData<Item> createDataObservable()
	{
		return Transformations.switchMap(itemIdInput, new Function<String, LiveData<Item>>()
		{
			@Override
			public LiveData<Item> apply(String itemId)
			{
				requestStateObservable.setValue(RequestState.LOADING);

				return Transformations.map(itemRepository.getItem(itemId), new Function<ResponseWrapper<Item>, Item>()
				{
					@Override
					public Item apply(ResponseWrapper<Item> input)
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

	public void setItemId(String itemId)
	{
		this.itemIdInput.setValue(itemId);
	}
}
