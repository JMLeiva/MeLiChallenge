package com.jml.melichallenge.view.mainsearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jml.melichallenge.R;

public class MainSearchActivity extends AppCompatActivity
{

	private final String FRAGMENT_TAG = "com.jml.MainSearchFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_search_activity);


		if (savedInstanceState == null)
		{
			createFragment();
		}
		else
		{
			Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

			if(fragment == null)
			{
				createFragment();
			}
		}
	}

	private void createFragment()
	{
		MainSearchFragment fragment = new MainSearchFragment();
		final FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
	}
}
