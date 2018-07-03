package com.jml.melichallenge.view.details;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.states.EntityState;
import com.jml.melichallenge.network.ConnectionManager;
import com.jml.melichallenge.repository.ItemRepository;
import com.jml.melichallenge.repository.ResponseWrapper;
import com.jml.melichallenge.view.common.EntityViewModel;

import javax.inject.Inject;

public class ItemViewModel extends EntityViewModel<Item>
{

	private MutableLiveData<String> itemIdInput;
	private ItemRepository itemRepository;

	@Inject
	ConnectionManager connectionManager;

	@Inject
	ItemViewModel(Application application, ItemRepository itemRepository)
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
				return getTransformationLiveData(itemId);
			}
		});
	}

	private LiveData<Item> getTransformationLiveData(String itemId)
	{
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

	@Override
	protected EntityState stateForResult(Item input)
	{
		if(input != null)
		{
			return EntityState.SUCCESSFULL;
		}
		else
		{
			if(!connectionManager.isInternetConnected())
			{
				return EntityState.NO_CONNECTION;
			}

			return EntityState.ERROR;
		}
	}
}
