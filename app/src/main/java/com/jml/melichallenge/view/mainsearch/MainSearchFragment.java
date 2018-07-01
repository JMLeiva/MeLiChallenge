package com.jml.melichallenge.view.mainsearch;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.view.common.AdapterClickListener;
import com.jml.melichallenge.view.details.DetailsActivity;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class MainSearchFragment extends Fragment implements PagedRecyclerViewAdapter.Paginator, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SearchView.OnSuggestionListener, AdapterClickListener<Item>
{
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout swiperefresh;
	MainSearchAdapter adapter;

	@BindView(R.id.include_welcome)
	View include_welcome;

	@BindView(R.id.include_empty)
	View include_empty;

	SearchViewModel viewModel;
	SearchTermViewModel searchTermViewModel;

	SearchCursorAdapter searchCursorAdapter;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
		searchTermViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchTermViewModel.class);
		searchCursorAdapter = new SearchCursorAdapter(getContext());
		setupObserver(viewModel);
		setupObserver(searchTermViewModel);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_search_fragment, container, false);

		ButterKnife.bind(this, view);

		adapter = new MainSearchAdapter(getContext(), this);
		adapter.setPaginator(this);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				viewModel.resetPaging();
			}
		});

		return view;
	}

	private void setupObserver(SearchViewModel viewModel)
	{
		// Update the list when the data changes
		viewModel.getDataObservable().observe(this, new Observer<SearchResult>()
		{
			@Override
			public void onChanged(@Nullable SearchResult searchResult)
			{
				swiperefresh.setRefreshing(false);
				adapter.stopLoading();

				if(searchResult.getPaging().getTotal() == 0)
				{
					showEmtpyState();
					return;
				}

				showResults();
				adapter.appendItems(searchResult.getResults());

			}
		});

		viewModel.getStateObservable().observe(this, new Observer<RequestState>()
		{
			@Override
			public void onChanged(@Nullable RequestState requestState)
			{

			}
		});

		viewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
		{
			@Override
			public void onChanged(@Nullable ErrorWrapper errorWrapper)
			{
				// TODO
				Toast.makeText(getContext(), errorWrapper.getMessage(), Toast.LENGTH_SHORT);
			}
		});
	}

	private void setupObserver(SearchTermViewModel searchTermViewModel)
	{
		searchTermViewModel.getDataObservable().observe(this, new Observer<List<String>>()
		{
			@Override
			public void onChanged(@Nullable List<String> strings)
			{
				searchCursorAdapter.onNewData(strings);
			}
		});
	}

	private void showEmtpyState()
	{
		include_empty.setVisibility(View.VISIBLE);
		swiperefresh.setVisibility(View.GONE);
	}

	private void showResults()
	{
		include_welcome.setVisibility(View.GONE);
		include_empty.setVisibility(View.GONE);
		swiperefresh.setVisibility(View.VISIBLE);
	}

	@Override
	public void loadNewPage()
	{
		viewModel.advance();
	}

	@Override
	public boolean hasMoreData()
	{
		SearchResult result = viewModel.getCurrentSearchResult();

		if(result == null) return true;
		return !result.getPaging().isFinished();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.main_search_menu, menu);

		SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search);

		// Setup SearchView
		SearchView searchView = (SearchView) searchMenuItem.getActionView();

		searchView.setQueryHint(getContext().getString(R.string.main_search_hint));
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		searchView.setOnSuggestionListener(this);

		searchCursorAdapter = new SearchCursorAdapter(getContext());
		searchView.setSuggestionsAdapter(searchCursorAdapter);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		if(query == null)
		{
			return false;
		}

		adapter.clear();
		viewModel.setQuery(new SearchQuery.Builder().site("MLA").qStr(query).pageSize(10).build());
		showResults();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{
		searchTermViewModel.setQuery(newText);
		return false;
	}


	@Override
	public boolean onClose()
	{
		return onQueryTextSubmit(null);
	}

	@Override
	public boolean onSuggestionSelect(int position)
	{

		return false;
	}

	@Override
	public boolean onSuggestionClick(int position)
	{
		String qStr = searchCursorAdapter.getSuggestion(position);
		onQueryTextSubmit(qStr);
		return false;
	}

	private void createAboutDialog()
	{
		/*Dialog progressDialog = new Dialog(getContext());
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setContentView(R.layout.about_dialog);

		WindowManager.LayoutParams lp = progressDialog.getWindow().getAttributes();
		lp.dimAmount = 0.0f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
		progressDialog.getWindow().setAttributes(lp);
		progressDialog.setCancelable(true);
		progressDialog.show();*/
	}

	@Override
	public void onClick(Item item)
	{
		Intent intent = new Intent(getContext(), DetailsActivity.class);
		intent.putExtra(DetailsActivity.ITEM_ID_EXTRA, item.getId());

		startActivity(intent);
	}
}
