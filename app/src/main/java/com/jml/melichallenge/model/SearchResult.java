package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class SearchResult
{
	private DTO dto;

	public SearchResult(DTO dto)
	{
		this.dto = dto;
	}

	public List<Item> getResults()
	{
		return dto.results;
	}

	public Paging getPaging()
	{
		return dto.paging;
	}

	public static class DTO
	{
		private List<Item> results;
		private String site_id;
		private String query;
		private Paging paging;

		// TODO Filters, Sorts
	}

	public static class Deserializer implements JsonDeserializer<SearchResult>
	{
		@Override
		public SearchResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			SearchResult.DTO dto = context.deserialize(json, SearchResult.DTO.class);
			return new SearchResult(dto);
		}
	}
}
