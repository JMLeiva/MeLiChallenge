package com.jml.melichallenge.repository;

import java.util.concurrent.ConcurrentHashMap;

public class ActionHandler<T>
{
	private final ConcurrentHashMap<Integer, ActionListener.Get<T>> getListeners;
	private final ConcurrentHashMap<Integer, ActionListener.Error> errorListeners;

	ActionHandler()
	{
		getListeners = new ConcurrentHashMap<>();
		errorListeners = new ConcurrentHashMap<>();
	}


	public void sendGet(T object)
	{
		synchronized (getListeners)
		{
			for (ActionListener.Get<T> getListener : getListeners.values())
			{
				getListener.onGet(object);
			}
		}
	}

	public void sendError()
	{
		synchronized (errorListeners)
		{
			for (ActionListener.Error errorListener : errorListeners.values())
			{
				errorListener.onError();
			}
		}
	}

	public void registerGet(ActionListener.Get<T> target)
	{
		synchronized (getListeners)
		{
			getListeners.put(target.hashCode(), target);
		}
	}

	public void registerError(ActionListener.Error target)
	{
		synchronized (errorListeners)
		{
			errorListeners.put(target.hashCode(), target);
		}
	}

	public void unregisterAll(Object target)
	{
		if (target == null)
		{
			return;
		}

		synchronized (getListeners)
		{
			if (getListeners != null)
			{
				getListeners.remove(target.hashCode());
			}
		}

		synchronized (errorListeners)
		{
			if (errorListeners != null)
			{
				errorListeners.remove(target.hashCode());
			}
		}
	}
}
