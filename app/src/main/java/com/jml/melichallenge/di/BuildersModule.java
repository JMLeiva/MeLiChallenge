package com.jml.melichallenge.di;

import com.jml.melichallenge.view.description.DescriptionFragment;
import com.jml.melichallenge.view.details.DetailsActivity;
import com.jml.melichallenge.view.details.DetailsFragment;
import com.jml.melichallenge.view.mainsearch.MainSearchActivity;
import com.jml.melichallenge.view.mainsearch.MainSearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

	@ContributesAndroidInjector
	abstract MainSearchActivity bindMainSearchActivity();

	@ContributesAndroidInjector
	abstract DetailsActivity bindDetailsActivity();

	@ContributesAndroidInjector
	abstract MainSearchFragment bindMainSearchFragment();

	@ContributesAndroidInjector
	abstract DetailsFragment bindDetailsFragment();

	@ContributesAndroidInjector
	abstract DescriptionFragment bindDescriptionFragment();
}