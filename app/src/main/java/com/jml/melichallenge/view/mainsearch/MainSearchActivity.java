package com.jml.melichallenge.view.mainsearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.jml.melichallenge.R;
import com.jml.melichallenge.view.common.BaseActivity;

/*
 *	Activity responsible for allowing the user to perform a search
 * */
public class MainSearchActivity extends BaseActivity
{
	private final String FRAGMENT_TAG = "com.jml.MainSearchFragment";

	@Override
	protected void createFragment()
	{
		Fragment fragment = new MainSearchFragment();
		final FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
	}

	@Override
	protected String[] getRootFragmentTags()
	{
		return new String[]{FRAGMENT_TAG};
	}
}
