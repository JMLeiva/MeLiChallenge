package com.jml.melichallenge.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jml.melichallenge.view.details.DescriptionViewModel;
import com.jml.melichallenge.view.details.ItemViewModel;
import com.jml.melichallenge.view.mainsearch.SearchTermViewModel;
import com.jml.melichallenge.view.mainsearch.SearchViewModel;
import com.jml.melichallenge.view.mainsearch.SitesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*ViewModel DI Providers */

@Module
abstract class ViewModelModule
{
	@Binds
	@IntoMap
	@ViewModelKey(SearchViewModel.class)
	abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(SearchTermViewModel.class)
	abstract ViewModel bindSearchTermViewModel(SearchTermViewModel searchTermViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(ItemViewModel.class)
	abstract ViewModel bindItemModel(ItemViewModel itemViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(DescriptionViewModel.class)
	abstract ViewModel bindDescriptionViewModel(DescriptionViewModel itemViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(SitesViewModel.class)
	abstract ViewModel bindSitesViewModel(SitesViewModel sitesViewModel);

	@Binds
	abstract ViewModelProvider.Factory bindViewModelFactory(MeLiViewModelProviderFactory factory);
}
