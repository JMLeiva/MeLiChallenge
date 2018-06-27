package com.jml.melichallenge.model;

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

	public static class DTO
	{
		private List<Item> results;
		private String site_id;
		private String query;
		private Paging paging;

		// TODO Filters, Sorts
	}


}
