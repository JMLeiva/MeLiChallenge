package com.jml.melichallenge.view.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jml.melichallenge.R;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_search_activity);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		if (savedInstanceState == null)
		{
			createFragment();
		}
		else
		{
			if(mustCreateFragment())
			{
				createFragment();
			}
		}
	}

	protected abstract void createFragment();
	protected abstract String[] getRootFragmentTags();

	private boolean mustCreateFragment()
	{
		for(String tag : getRootFragmentTags())
		{
			Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

			if(fragment == null)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
