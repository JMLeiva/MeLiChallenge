package com.jml.melichallenge.view.mainsearch;

import android.view.View;
import android.widget.TextView;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Item;
import com.jmleiva.pagedrecyclerview.PagedViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchItemViewHolder extends PagedViewHolder
{
	@BindView(R.id.tv_name)
	TextView tv_name;

	public SearchItemViewHolder(View itemView)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void setup(Item item)
	{
		this.tv_name.setText(item.getTitle());
	}
}
