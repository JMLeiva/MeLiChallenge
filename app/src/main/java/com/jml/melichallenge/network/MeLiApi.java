package com.jml.melichallenge.network;


import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SearchResult;


public interface MeLiApi
{
	void search(String siteId, String qStr, int offset, int limit, ApiCallback<SearchResult> callback);
	void getItem(String itemId, ApiCallback<Item> callback);
}
