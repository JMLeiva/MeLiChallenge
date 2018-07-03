package com.jml.melichallenge.view.common;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

// Useful to observe LiveData observables in which other LiveData are backed using tansformation
public class BlindObserver<T> implements Observer<T>
{
	@Override
	public void onChanged(@Nullable T t)
	{
		// Do absolutely nothing
	}
}
