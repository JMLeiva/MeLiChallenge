package com.jml.melichallenge.view.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SelectableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
	private RecyclerViewClickListener listener;

	protected SelectableViewHolder(View itemView, RecyclerViewClickListener listener)
	{
		super(itemView);
		this.listener = listener;
		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if(listener != null)
		{
			listener.onClick(this.itemView, this.getAdapterPosition());
		}
	}
}
