package com.jml.melichallenge.view.mainsearch;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.RequestState;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;
import com.jml.melichallenge.view.mainsearch.viewmodel.SearchViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainSearchFragment extends Fragment implements PagedRecyclerViewAdapter.Paginator, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SearchView.OnSuggestionListener
{
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout swiperefresh;
	MainSearchAdapter adapter;

	SearchViewModel viewModel;


	// TODO esto deberia estar en otro lado
	SearchResult lastResult;
	boolean hideLoading = true;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
		setupObserver(viewModel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_search_fragment, container, false);

		ButterKnife.bind(this, view);

		adapter = new MainSearchAdapter(getContext());
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
		viewModel.getSearchObservable().observe(this, new Observer<SearchResult>()
		{
			@Override
			public void onChanged(@Nullable SearchResult searchResult)
			{
				adapter.appendItems(searchResult.getResults());
				swiperefresh.setRefreshing(false);
				adapter.stopLoading();
				lastResult = searchResult;
			}
		});

		viewModel.getStateObservable().observe(this, new Observer<RequestState>()
		{
			@Override
			public void onChanged(@Nullable RequestState requestState)
			{
				// TODO
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

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void loadNewPage()
	{
		viewModel.advance();
	}

	@Override
	public boolean hasMoreData()
	{
		if(lastResult == null ) return !hideLoading;
		return !lastResult.getPaging().isFinished();
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

		//searchCursorAdapter = new SearchCursorAdapter(this);
		//searchView.setSuggestionsAdapter(searchCursorAdapter);

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
		hideLoading = false;
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{
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
		//searchCursorAdapter.setQuery(newText);
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position)
	{
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
}
