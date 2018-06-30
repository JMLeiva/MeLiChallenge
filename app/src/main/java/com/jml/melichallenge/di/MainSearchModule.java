/*package com.jml.melichallenge.di;


import dagger.Module;
import dagger.Provides;

@Module
public class MainSearchModule {

	private MainSearchContract.View view;

	public MainSearchModule(MainSearchContract.View view) {
		this.view = view;
	}

	@Provides
	public MainSearchContract.View provideView() {
		return view;
	}

	@Provides
	public MainSearchContract.Presenter providePresenter(MainSearchContract.View mainSearchView) {
		return new MainSearchPresenter(mainSearchView);
	}
}*/