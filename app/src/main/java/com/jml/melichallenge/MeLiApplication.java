package com.jml.melichallenge;

import android.app.Activity;
import android.app.Application;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.jml.melichallenge.di.DaggerMeLiAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MeLiApplication extends Application implements HasActivityInjector
{
	@Inject
	DispatchingAndroidInjector<Activity> dispatchingAndroidInjectorActivity;

	@Override
	public void onCreate() {
		super.onCreate();
		DaggerMeLiAppComponent.builder()
				.application(this)
				.build()
				.inject(this);


		BigImageViewer.initialize(GlideImageLoader.with(this));
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjectorActivity;
	}
}
