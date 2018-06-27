package com.jml.melichallenge.model;

public class Reviews
{
	private DTO dto;

	public Reviews(DTO dto)
	{
		this.dto = dto;
	}

	private static class DTO
	{
		private float rating_average;
		private float total;
	}
}
