package com.jml.melichallenge.view.common;

import android.support.v4.app.Fragment;
import android.view.View;

import com.jml.melichallenge.R;
import com.jml.melichallenge.network.ConnectionManager;

import javax.inject.Inject;

import butterknife.BindView;

public abstract class BaseFragment extends Fragment
{
	@Inject
	protected
	ConnectionManager connectionManager;

	@BindView(R.id.include_no_connection)
	View include_no_connection;

	protected void setupUI()
	{
		include_no_connection.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onRetryNoConnection();
			}
		});
	}

	protected abstract void onRetryNoConnection();

	protected void showNoConnection()
	{
		include_no_connection.setVisibility(View.VISIBLE);

	}

	protected void hideNoConnection()
	{
		include_no_connection.setVisibility(View.GONE);

	}
}
