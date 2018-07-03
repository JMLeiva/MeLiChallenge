package com.jml.melichallenge.view.details;

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
	private MutableLiveData<String> itemIdInput;
	private ItemRepository itemRepository;

	@Inject
	public ItemViewModel(Application application, ItemRepository itemRepository)
	{
		super(application);
		this.itemRepository = itemRepository;
	}

	@Override
	protected LiveData<Item> createDataObservable()
	{
		itemIdInput = new MutableLiveData<>();

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
