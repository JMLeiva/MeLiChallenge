package com.jml.melichallenge.network;


import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SearchResult;


public interface MeLiApi
{
	void search(String siteId, String qStr, int offset, int limit, String sort, ApiCallback<SearchResult> callback);
	void getItem(String itemId, ApiCallback<Item> callback);
	void getItemDescription(String itemId, ApiCallback<Description> callback);
}
