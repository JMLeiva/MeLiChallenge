package com.jml.melichallenge.model;

public class Paging
{
	private DTO dto;

	public Paging(DTO dto)
	{
		this.dto = dto;
	}

	private static class DTO
	{
		private int total;
		private int offset;
		private int limit;
		private int primary_results;
	}
}
