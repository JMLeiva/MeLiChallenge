package com.jml.melichallenge.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.jml.melichallenge.MeLiApplication;
import com.jml.melichallenge.network.MeLiApi;
import com.jml.melichallenge.network.MeLiApiImpl;
import com.jml.melichallenge.repository.DescriptionRepository;
import com.jml.melichallenge.repository.ItemRepository;
import com.jml.melichallenge.repository.SearchTermlRepository;
import com.jml.melichallenge.repository.persistence.MeLiDatabase;
import com.jml.melichallenge.utils.BackgroundRunner;

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

	@Provides
	ItemRepository providesItemRepository(MeLiApi meLiApi) {
		return new ItemRepository(meLiApi);
	}

	@Singleton
	@Provides
	MeLiDatabase providesMeLiDatabase(Context context) {
		return MeLiDatabase.create(context);
	}

	@Singleton
	@Provides
	DescriptionRepository providesDescriptionRepository(MeLiApi meLiApi) {
		return new DescriptionRepository(meLiApi);
	}


	@Singleton
	@Provides
	BackgroundRunner providesBackgroundRunner()
	{
		return new BackgroundRunner();
	}

	@Singleton
	@Provides
	SearchTermlRepository providesSearchTermRepository(MeLiDatabase meLiDatabase) {
		return new SearchTermlRepository(meLiDatabase);
	}
}