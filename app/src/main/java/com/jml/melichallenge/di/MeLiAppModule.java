package com.jml.melichallenge.di;

import android.app.Application;
import android.content.Context;

import com.jml.melichallenge.MeLiApplication;
import com.jml.melichallenge.network.MeLiApi;
import com.jml.melichallenge.network.MeLiApiImpl;
import com.jml.melichallenge.repository.DescriptionRepository;
import com.jml.melichallenge.repository.ItemRepository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class MeLiAppModule
{
	@Provides
	Context provideContext(MeLiApplication application) {
		return application.getApplicationContext();
	}

	@Provides
	Application provideApplication(MeLiApplication application) {
		return application;
	}

	@Provides
	MeLiApi providesMeLiApi() {
		return new MeLiApiImpl();
	}

	@Singleton
	@Provides
	ItemRepository providesItemRepository(MeLiApi meLiApi) {
		return new ItemRepository(meLiApi);
	}

	@Singleton
	@Provides
	DescriptionRepository providesDescriptionRepository(MeLiApi meLiApi) {
		return new DescriptionRepository(meLiApi);
	}
}