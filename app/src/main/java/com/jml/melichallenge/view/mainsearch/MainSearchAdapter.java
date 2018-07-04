package com.jml.melichallenge.view.mainsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.view.common.AdapterClickListener;
import com.jml.melichallenge.view.common.RecyclerViewClickListener;
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;
import com.jmleiva.pagedrecyclerview.PagedViewHolder;

import java.util.ArrayList;
import java.util.List;

/*
 *	Adapter that lists the search results
 * */
public class MainSearchAdapter extends PagedRecyclerViewAdapter<SearchItemViewHolder, PagedViewHolder> implements RecyclerViewClickListener
{
	private List<Item> items;
	private Context context;

	private AdapterClickListener<Item> listener;


	MainSearchAdapter(Context context, AdapterClickListener<Item> listener)
	{
		this.context = context;
		this.items = new ArrayList<>();
		this.listener = listener;
	}

	public void clear()
	{
		this.items.clear();
		notifyDataSetChanged();
	}

	public void appendItems(List<Item> list)
	{
		this.items.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	protected int getPagedItemCount()
	{
		return items.size();
	}

	@Override
	protected SearchItemViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_layout, parent, false);
		return new SearchItemViewHolder(view, this);
	}

	@Override
	protected PagedViewHolder onCreateLoadingViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_row_layout, parent, false);
		return new PagedViewHolder(view);
	}

	@Override
	protected void onBindNormalViewHolder(SearchItemViewHolder holder, int position)
	{
		holder.setup(context, items.get(position));
	}

	@Override
	protected void onBindLoadingViewHolder(PagedViewHolder holder)
	{
		// No need to bind anything in here
	}

	@Override
	public void onClick(View view, int position)
	{
		this.listener.onClick(items.get(position));
	}
}
