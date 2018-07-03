package com.jml.melichallenge.view.mainsearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.model.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortAdapter extends BaseAdapter
{
	private class IdNameSortWrapper
	{
		public IdName idName;
		public boolean checked;

		public IdNameSortWrapper(IdName idName, boolean checked)
		{
			this.idName = idName;
			this.checked = checked;
		}
	}

	private List<IdNameSortWrapper> sorts;

	public SortAdapter(SearchResult searchResult)
	{
		sorts = new ArrayList<>();
		sorts.add(new IdNameSortWrapper(searchResult.getSort(), true));

		for(IdName idName : searchResult.getAvailableSorts())
		{
			sorts.add(new IdNameSortWrapper(idName, false));
		}


		Collections.sort(sorts, new Comparator<IdNameSortWrapper>()
		{
			@Override
			public int compare(IdNameSortWrapper o1, IdNameSortWrapper o2)
			{
				return o1.idName.getName().compareTo(o2.idName.getName());
			}
		});
	}

	@Override
	public int getCount()
	{
		return sorts.size();
	}

	@Override
	public Object getItem(int position)
	{
		return sorts.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	public IdName idNameAtPosition(int position)
	{
		return sorts.get(position).idName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_single_choice, parent, false);
		}

		CheckedTextView checkedTextView = (CheckedTextView)convertView;

		IdNameSortWrapper idNameSortWrapper = (IdNameSortWrapper)getItem(position);

		checkedTextView.setText(idNameSortWrapper.idName.getName());

		return convertView;
	}

	public int checkedPosition()
	{
		int position = 0;

		for(IdNameSortWrapper idNameSortWrapper : sorts)
		{
			if (idNameSortWrapper.checked)
			{
				break;
			}
			position++;
		}

		return position;
	}
}
