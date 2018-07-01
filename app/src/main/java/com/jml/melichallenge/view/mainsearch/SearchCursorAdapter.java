package com.jml.melichallenge.view.mainsearch;

import android.app.SearchManager;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;

import com.jml.melichallenge.R;

import java.util.ArrayList;
import java.util.List;

public class SearchCursorAdapter extends SimpleCursorAdapter
{
	private List<String> list = new ArrayList<>();

	public SearchCursorAdapter(Context context)
	{
		super(context, R.layout.search_list_item, null, new String[] { SearchManager.SUGGEST_COLUMN_TEXT_1 }, new int[] { android.R.id.text1 }, 0);
	}


	public void onNewData(List<String> object)
	{
		this.list = object;
		refresh();
	}

	private void refresh()
	{
		String[] sAutocompleteColNames = new String[] {
				BaseColumns._ID,                         // necessary for adapter
				SearchManager.SUGGEST_COLUMN_TEXT_1 };     // the full search term

		MatrixCursor cursor = new MatrixCursor(sAutocompleteColNames);


		// parse your search terms into the MatrixCursor
		int index = 0;
		for (String aFiltered : list)
		{
			Object[] row = new Object[]{index, aFiltered};
			cursor.addRow(row);
			index++;
		}

		changeCursor(cursor);
	}

	public String getSuggestion(int position)
	{
		return ((Cursor)getItem(position)).getString(1);
	}
}