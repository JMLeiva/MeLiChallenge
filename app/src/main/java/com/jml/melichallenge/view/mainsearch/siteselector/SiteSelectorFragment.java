package com.jml.melichallenge.view.mainsearch.siteselector;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.model.states.EntityListState;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.view.common.AdapterClickListener;
import com.jml.melichallenge.view.common.BaseFragment;
import com.jml.melichallenge.view.mainsearch.SitesViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class SiteSelectorFragment extends BaseFragment implements AdapterClickListener<IdName>
{
	SitesViewModel sitesViewModel;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.include_empty)
	View include_empty;

	@BindView(R.id.pb_loading)
	ProgressBar pb_loading;

	@BindView(R.id.tv_emptyTextView)
	TextView tv_emptyTextView;

	SiteSelectorAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		sitesViewModel = ViewModelProviders.of(this, viewModelFactory).get(SitesViewModel.class);
		setupObserver(sitesViewModel);
		sitesViewModel.get();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.site_selector_fragment, container, false);
		ButterKnife.bind(this, view);

		setupUI();

		return view;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();

		tv_emptyTextView.setText(R.string.site_emtpyStateText);

		adapter = new SiteSelectorAdapter(this);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	}

	@Override
	protected void onRetryNoConnection()
	{
		showResults();
	}

	private void setupObserver(final SitesViewModel sitesViewModel)
	{
		sitesViewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
		{
			@Override
			public void onChanged(@Nullable ErrorWrapper errorWrapper)
			{
				if(connectionManager.isInternetConnected())
				{
					String errorMsg = errorWrapper != null ? errorWrapper.getMessage() : null;

					if (errorMsg != null)
					{
						Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		sitesViewModel.getEntityListStateObserbale().observe(this, new Observer<EntityListState>()
		{
			@Override
			public void onChanged(EntityListState entityListState)
			{
				switch (entityListState)
				{
					case SUCCESSFULL:
						adapter.setData(sitesViewModel.getData());
						showResults();
						break;
					case NO_CONNECTION:
						showNoConnection();
						break;
					case NO_RESULTS:
						showEmtpyState();
						break;
				}
			}
		});
	}

	private void showEmtpyState()
	{
		include_empty.setVisibility(View.VISIBLE);
		hideNoConnection();
	}

	private void showResults()
	{
		include_empty.setVisibility(View.GONE);
		hideNoConnection();
		pb_loading.setVisibility(View.GONE);
	}

	@Override
	protected void showNoConnection()
	{
		super.showNoConnection();
		include_empty.setVisibility(View.GONE);
	}

	@Override
	public void onClick(IdName item)
	{
		if(getActivity() != null)
		{
			Intent data = new Intent();
			data.putExtra(SiteSelectorActivity.SELECTED_ID_NAME_EXTRA, item.getId());

			getActivity().setResult(Activity.RESULT_OK, data);
			getActivity().finish();
		}
	}
}
