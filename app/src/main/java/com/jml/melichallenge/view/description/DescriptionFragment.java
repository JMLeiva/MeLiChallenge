package com.jml.melichallenge.view.description;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.states.EntityState;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.view.details.DescriptionViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.jml.melichallenge.view.details.DetailsActivity.ITEM_ID_EXTRA;

public class DescriptionFragment extends Fragment
{
	@BindView(R.id.include_description)
	View include_description;

	@BindView(R.id.tv_descriptionBody)
	TextView tv_descriptionBody;

	@BindView(R.id.pb_description)
	ProgressBar pb_description;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	DescriptionViewModel descriptionViewModel;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.description_fragment, container, false);
		ButterKnife.bind(this, view);


		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		descriptionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DescriptionViewModel.class);
		setupObserver(descriptionViewModel);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		if(getArguments() != null)
		{
			String itemIdExtra = getArguments().getString(ITEM_ID_EXTRA);
			descriptionViewModel.setItemId(itemIdExtra);
		}
	}

	private void setupObserver(final DescriptionViewModel descriptionViewModel)
	{
		descriptionViewModel.getEntityStateObserbale().observe(this, new Observer<EntityState>()
		{
			@Override
			public void onChanged(@Nullable EntityState entityState)
			{
				switch (entityState)
				{
					case SUCCESSFULL:
						setupUI(descriptionViewModel.getData());
						break;
					case NO_CONNECTION:
						// TODO
						break;
				}
			}
		});

		descriptionViewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
		{
			@Override
			public void onChanged(@Nullable ErrorWrapper errorWrapper)
			{
				if(errorWrapper == null)
				{
					return;
				}

				String errorMsg = errorWrapper.getMessage();

				if(errorMsg != null)
				{
					Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void setupUI(Description description)
	{
		setupDescription(description);
	}

	private void setupDescription(Description description)
	{
		pb_description.setVisibility(View.GONE);
		tv_descriptionBody.setText(description.getText());

		include_description.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO
			}
		});
	}
}
