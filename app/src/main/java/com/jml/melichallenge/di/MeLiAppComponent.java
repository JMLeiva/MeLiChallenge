package com.jml.melichallenge.di;

import com.jml.melichallenge.MeLiApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
						AndroidInjectionModule.class,
						MeLiAppModule.class,
						ViewModelModule.class,
						BuildersModule.class})
public interface MeLiAppComponent extends AndroidInjector<MeLiApplication>
{
	@Component.Builder
	interface Builder {
		@BindsInstance
		Builder application(MeLiApplication application);
		MeLiAppComponent build();
	}
	void inject(MeLiApplication app);
}