package com.jml.melichallenge.model;

public class Attribute
{
	private DTO dto;

	public Attribute(DTO dto)
	{
		this.dto = dto;
	}

	private static class DTO
	{
		private String id;
		private String name;
		private String value_name;
	}
}
