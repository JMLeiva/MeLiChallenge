package com.jml.melichallenge.view.mainsearch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.SearchQuery;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.SearchRepository;
import com.jml.melichallenge.view.viewmodel.SearchViewModel;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainSearchFragment extends Fragment implements PagedRecyclerViewAdapter.Paginator
{
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout swiperefresh;
	MainSearchAdapter adapter;

	SearchViewModel viewModel;

	SearchResult lastResult;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
		viewModel.setQuery(new SearchQuery.Builder().site("MLA").qStr("phyrexian arena foil").pageSize(10).build());

		setupObserver(viewModel);
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
		if(lastResult == null) return false;
		return !lastResult.getPaging().isFinished();
	}
}
