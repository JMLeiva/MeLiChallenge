package com.jml.melichallenge.view.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jml.melichallenge.R;
import com.jml.melichallenge.view.common.BaseActivity;

public class DetailsActivity extends BaseActivity
{
	public static final String ITEM_ID_EXTRA = "com.jml.DetailsActivity.ITEM_ID_EXTRA";
	private final String FRAGMENT_TAG = "com.jml.DetailsFragment";


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle(R.string.item);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void createFragment()
	{
		String itemIdExtra = getIntent().getStringExtra(ITEM_ID_EXTRA);
		Bundle args = new Bundle();
		args.putString(ITEM_ID_EXTRA, itemIdExtra);

		Fragment fragment = new DetailsFragment();
		fragment.setArguments(args);
		final FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
	}

	@Override
	protected String getFragmentTag()
	{
		return FRAGMENT_TAG;
	}
}