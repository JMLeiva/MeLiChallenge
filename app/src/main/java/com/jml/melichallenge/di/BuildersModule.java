package com.jml.melichallenge.di;

import com.jml.melichallenge.view.description.DescriptionFragment;
import com.jml.melichallenge.view.details.DetailsActivity;
import com.jml.melichallenge.view.details.DetailsFragment;
import com.jml.melichallenge.view.mainsearch.MainSearchActivity;
import com.jml.melichallenge.view.mainsearch.MainSearchFragment;
import com.jml.melichallenge.view.mainsearch.siteselector.SiteSelectorActivity;
import com.jml.melichallenge.view.mainsearch.siteselector.SiteSelectorFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/* DI Module for platform object injection */

@Module
public abstract class BuildersModule {

	@ContributesAndroidInjector
	abstract MainSearchActivity bindMainSearchActivity();

	@ContributesAndroidInjector
	abstract DetailsActivity bindDetailsActivity();

	@ContributesAndroidInjector
	abstract SiteSelectorActivity bindSiteSelectorActivity();

	@ContributesAndroidInjector
	abstract MainSearchFragment bindMainSearchFragment();

	@ContributesAndroidInjector
	abstract DetailsFragment bindDetailsFragment();

	@ContributesAndroidInjector
	abstract DescriptionFragment bindDescriptionFragment();

	@ContributesAndroidInjector
	abstract SiteSelectorFragment bindSiteSelectorFragment();
}