package com.jml.melichallenge.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jml.melichallenge.view.mainsearch.viewmodel.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule
{
	@Binds
	@IntoMap
	@ViewModelKey(SearchViewModel.class)
	abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

	@Binds
	abstract ViewModelProvider.Factory bindViewModelFactory(MeLiViewModelProviderFactory factory);
}
