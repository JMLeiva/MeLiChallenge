package com.jml.melichallenge.view.mainsearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.repository.ActionListener;
import com.jml.melichallenge.repository.SearchRepository;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainSearchFragment extends Fragment implements PagedRecyclerViewAdapter.Paginator, ActionListener.Get<SearchResult>,ActionListener.Error
{
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout swiperefresh;

	MainSearchAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

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
				SearchRepository.getInstance().search("MLA", "PLAYSTATION");
			}
		});

		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();

		SearchRepository.getInstance().getActionHandler().registerGet(this);
		SearchRepository.getInstance().getActionHandler().registerError(this);


	}

	@Override
	public void onPause()
	{
		super.onPause();
		SearchRepository.getInstance().getActionHandler().unregisterAll(this);
	}

	@Override
	public void loadNewPage()
	{
		// TODO
	}

	@Override
	public boolean hasMoreData()
	{
		return true;
	}

	@Override
	public void onGet(SearchResult object)
	{
		adapter.setItems(object.getResults());

		swiperefresh.setRefreshing(false);
	}

	@Override
	public void onError()
	{

	}
}
