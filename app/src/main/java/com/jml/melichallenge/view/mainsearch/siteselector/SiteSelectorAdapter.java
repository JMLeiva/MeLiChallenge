package com.jml.melichallenge.view.mainsearch.siteselector;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.view.common.AdapterClickListener;
import com.jml.melichallenge.view.common.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SiteSelectorAdapter extends RecyclerView.Adapter<SiteSelectorViewHolder> implements RecyclerViewClickListener
{
	private List<IdName> data;
	private AdapterClickListener<IdName> listener;

	SiteSelectorAdapter(AdapterClickListener<IdName> listener)
	{
		this.data = new ArrayList<>();
		this.listener = listener;
	}

	public void setData(List<IdName> data)
	{
		this.data.clear();
		this.data.addAll(data);

		Collections.sort(this.data, new Comparator<IdName>()
		{
			@Override
			public int compare(IdName o1, IdName o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		});

		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public SiteSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_selector_item, parent, false);
		return new SiteSelectorViewHolder(view, this);
	}

	@Override
	public void onBindViewHolder(@NonNull SiteSelectorViewHolder holder, int position)
	{
		holder.setup(data.get(position));
	}

	@Override
	public int getItemCount()
	{
		return data.size();
	}

	@Override
	public void onClick(View view, int position)
	{
		if(listener != null)
		{
			listener.onClick(data.get(position));
		}
	}
}
