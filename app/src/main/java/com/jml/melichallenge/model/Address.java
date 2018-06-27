package com.jml.melichallenge.model;

public class Address
{
	private DTO dto;

	public Address(DTO dto)
	{
		this.dto = dto;
	}

	private static class DTO
	{
		private String state_id;
		private String state_name;
		private String city_id;
		private String city_name;
	}
}
