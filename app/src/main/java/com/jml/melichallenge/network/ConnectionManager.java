package com.jml.melichallenge.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConnectionManager
{
	private ConnectivityManager connectivityManager;

	@Inject
	public ConnectionManager(Context context)
	{
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean isInternetConnected()
	{
		if(connectivityManager == null)
		{
			//Default if ConectivityService is not available for any reason
			return true;
		}

		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}