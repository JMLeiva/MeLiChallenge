package com.jml.melichallenge.repository;

import java.util.concurrent.ConcurrentHashMap;

public class ObserverHandler<T>
{
	private final ConcurrentHashMap<Integer, RepositoryObserver.Get<T>> getListeners;
	private final ConcurrentHashMap<Integer, RepositoryObserver.Error> errorListeners;

	ObserverHandler()
	{
		getListeners = new ConcurrentHashMap<>();
		errorListeners = new ConcurrentHashMap<>();
	}


	public void sendGet(T object)
	{
		synchronized (getListeners)
		{
			for (RepositoryObserver.Get<T> getListener : getListeners.values())
			{
				getListener.onGet(object);
			}
		}
	}

	public void sendError()
	{
		synchronized (errorListeners)
		{
			for (RepositoryObserver.Error errorListener : errorListeners.values())
			{
				errorListener.onError();
			}
		}
	}

	public void registerGet(RepositoryObserver.Get<T> target)
	{
		synchronized (getListeners)
		{
			getListeners.put(target.hashCode(), target);
		}
	}

	public void registerError(RepositoryObserver.Error target)
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
