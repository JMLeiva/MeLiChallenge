package com.jml.melichallenge.view.mainsearch;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jml.melichallenge.R;

public class MainSearchActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_search_activity);

		MainSearchFragment fragment = new MainSearchFragment();
		final FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
	}
}
