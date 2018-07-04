package com.jml.melichallenge.view.mainsearch.siteselector;

import android.view.View;
import android.widget.TextView;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.view.common.RecyclerViewClickListener;
import com.jml.melichallenge.view.common.SelectableViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteSelectorViewHolder extends SelectableViewHolder
{
	@BindView(R.id.tv_name)
	TextView tv_name;

	SiteSelectorViewHolder(View itemView, RecyclerViewClickListener listener)
	{
		super(itemView, listener);
		ButterKnife.bind(this, itemView);
	}

	public void setup(IdName idName)
	{
		tv_name.setText(idName.getName());
	}
}
