package com.jml.melichallenge.view.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.jml.melichallenge.R;
import com.jml.melichallenge.view.common.BaseActivity;

/*
 *	Activity responsible the details of an Item
 * */
public class DetailsActivity extends BaseActivity
{
	public static final String ITEM_ID_EXTRA = "com.jml.DetailsActivity.ITEM_ID_EXTRA";

	public static final String FRAGMENT_DETAILS_TAG = "com.jml.FRAGMENT_DETAILS_TAG";
	public static final String FRAGMENT_GALLERY_TAG = "com.jml.FRAGMENT_GALLERY_TAG";
	public static final String FRAGMENT_DESCRIPTION_TAG = "com.jml.FRAGMENT_DESCRIPTION_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();

		if(actionBar != null)
		{
			getSupportActionBar().setTitle(R.string.item);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected String[] getRootFragmentTags()
	{
		return new String[]{FRAGMENT_DETAILS_TAG};
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
		manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_DETAILS_TAG).commit();
	}
}