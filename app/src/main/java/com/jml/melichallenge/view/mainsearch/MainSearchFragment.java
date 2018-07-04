package com.jml.melichallenge.view.mainsearch;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.model.states.EntityListState;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.view.common.AdapterClickListener;
import com.jml.melichallenge.view.common.BaseFragment;
import com.jml.melichallenge.view.details.DetailsActivity;
import com.jml.melichallenge.view.mainsearch.siteselector.SiteSelectorActivity;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/*
 *	Fragment responsible for allowing the user to perform a search
 * */
public class MainSearchFragment extends BaseFragment implements PagedRecyclerViewAdapter.Paginator, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SearchView.OnSuggestionListener, AdapterClickListener<Item>
{
	final static String SAVE_STATE_QUERY_STR = "jml.melichallenge.SAVE_STATE_QUERY_STR";
	private static final int SELECT_SITE_REQUEST_CODE = 1;

	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout swiperefresh;
	MainSearchAdapter adapter;

	@BindView(R.id.include_welcome)
	View include_welcome;

	@BindView(R.id.include_empty)
	View include_empty;

	SearchViewModel searchViewModel;
	SearchTermViewModel searchTermViewModel;

	SearchCursorAdapter searchCursorAdapter;
	SearchView searchView;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	SearchQuery savedInstanceSearchQuery;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if(savedInstanceState != null)
		{
			Object searchQuery = savedInstanceState.getParcelable(SAVE_STATE_QUERY_STR);
			if(searchQuery instanceof  SearchQuery)
			{
				savedInstanceSearchQuery = (SearchQuery)searchQuery;
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
		searchTermViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchTermViewModel.class);
		searchCursorAdapter = new SearchCursorAdapter(getContext());
		setupObserver(searchViewModel);
		setupObserver(searchTermViewModel);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_search_fragment, container, false);
		ButterKnife.bind(this, view);

		setupUI();

		return view;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		adapter = new MainSearchAdapter(getContext(), this);
		adapter.setPaginator(this);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				searchViewModel.resetPaging();
			}
		});
	}

	@Override
	protected void onRetryNoConnection()
	{
		showResults();
	}

	private void setupObserver(SearchViewModel viewModel)
	{
		viewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
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

		searchViewModel.getEntityListStateObserbale().observe(this, new Observer<EntityListState>()
		{
			@Override
			public void onChanged(EntityListState entityListState)
			{
				swiperefresh.setRefreshing(false);
				adapter.stopLoading();

				switch (entityListState)
				{
					case SUCCESSFULL:
						adapter.appendItems(searchViewModel.getData().getResults());
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
		include_welcome.setVisibility(View.GONE);
		getActivity().invalidateOptionsMenu();
		hideNoConnection();
	}

	private void showResults()
	{
		include_welcome.setVisibility(View.GONE);
		include_empty.setVisibility(View.GONE);
		swiperefresh.setVisibility(View.VISIBLE);
		hideNoConnection();
		getActivity().invalidateOptionsMenu();
	}

	@Override
	protected void showNoConnection()
	{
		super.showNoConnection();
		include_empty.setVisibility(View.GONE);
		swiperefresh.setVisibility(View.GONE);
		include_welcome.setVisibility(View.GONE);
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void loadNewPage()
	{
		searchViewModel.advance();
	}

	@Override
	public boolean hasMoreData()
	{
		SearchResult result = searchViewModel.getCurrentSearchResult();

		if (result == null) return true;
		return !result.getPaging().isFinished();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.main_search_menu, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search);

		SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

		if(searchManager != null)
		{
			// Setup SearchView
			searchView = (SearchView) searchMenuItem.getActionView();

			searchView.setQueryHint(getContext().getString(R.string.main_search_hint));
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
			searchView.setSubmitButtonEnabled(true);
			searchView.setOnQueryTextListener(this);
			searchView.setOnCloseListener(this);
			searchView.setOnSuggestionListener(this);

			searchCursorAdapter = new SearchCursorAdapter(getContext());
			searchView.setSuggestionsAdapter(searchCursorAdapter);
		}
		else
		{
			searchMenuItem.setVisible(false);
		}



		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu (Menu menu)
	{
		MenuItem sortMenuItem = menu.findItem(R.id.action_sort);
		sortMenuItem.setVisible(searchViewModel.getCurrentSearchResult() != null);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_about:
				createAboutDialog();
				return true;
			case R.id.action_sort:
				createSortDialog();
				return true;
			case R.id.action_site:
				goToSiteSelection();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		if (query == null)
		{
			return false;
		}

		adapter.clear();
		searchViewModel.setQueryStr(query);
		searchTermViewModel.addNewTerm(query);
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
		searchView.setQuery(qStr, true);
		return false;
	}

	private void createAboutDialog()
	{
		Dialog dialog = new Dialog(getContext());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.about_dialog);

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.0f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
		dialog.getWindow().setAttributes(lp);
		dialog.setCancelable(true);
		dialog.show();
	}

	private void createSortDialog()
	{
		if(searchViewModel.getCurrentSearchResult() == null)
		{
			return;
		}

		final SortAdapter sortAdapter = new SortAdapter(searchViewModel.getCurrentSearchResult());

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).
				setSingleChoiceItems(sortAdapter, sortAdapter.checkedPosition(), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				IdName sort = sortAdapter.idNameAtPosition(which);
				searchViewModel.setSorting(sort.getId());
				adapter.clear();
				dialog.dismiss();
			}
		});

		builder.setCancelable(true);
		builder.setTitle(R.string.sort);
		builder.create().show();
	}

	private void goToSiteSelection()
	{
		Intent intent = new Intent(getContext(), SiteSelectorActivity.class);
		startActivityForResult(intent, SELECT_SITE_REQUEST_CODE);
	}

	@Override
	public void onClick(Item item)
	{
		Intent intent = new Intent(getContext(), DetailsActivity.class);
		intent.putExtra(DetailsActivity.ITEM_ID_EXTRA, item.getId());

		startActivity(intent);
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState)
	{
		outState.putParcelable(SAVE_STATE_QUERY_STR, searchViewModel.getQuery().getValue());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == SELECT_SITE_REQUEST_CODE)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				String siteId = data.getStringExtra(SiteSelectorActivity.SELECTED_ID_NAME_EXTRA);

				if(siteId != null)
				{
					searchViewModel.setSite(siteId);
					adapter.clear();
				}
			}

			return;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
