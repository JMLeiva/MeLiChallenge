package com.jml.melichallenge.di;

import com.jml.melichallenge.view.mainsearch.MainSearchActivity;
import com.jml.melichallenge.view.mainsearch.MainSearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

	@ContributesAndroidInjector
	abstract MainSearchActivity bindMainSearchActivity();

	@ContributesAndroidInjector
	abstract MainSearchFragment bindMainSearchFragment();
	// Add bindings for other sub-components here
}