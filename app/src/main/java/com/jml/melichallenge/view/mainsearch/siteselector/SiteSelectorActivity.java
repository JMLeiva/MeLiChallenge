package com.jml.melichallenge.view.mainsearch.siteselector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.jml.melichallenge.R;
import com.jml.melichallenge.view.common.BaseActivity;

/*
 *	Activity responsible for showing a list to select the search region
 * */
public class SiteSelectorActivity extends BaseActivity
{
	public static final String SELECTED_ID_NAME_EXTRA = "com.jml.SELECTED_ID_NAME_EXTRA";

	private static final String FRAGMENT_TAG = "com.jml.SiteSelectorFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();

		if(actionBar != null)
		{
			getSupportActionBar().setTitle(R.string.sites);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void createFragment()
	{
		Fragment fragment = new SiteSelectorFragment();
		final FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
	}

	@Override
	protected String[] getRootFragmentTags()
	{
		return new String[]{FRAGMENT_TAG};
	}
}
