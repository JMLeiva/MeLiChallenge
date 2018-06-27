package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

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

	public static class Deserializer implements JsonDeserializer<Address>
	{
		@Override
		public Address deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Address.DTO dto = context.deserialize(json, Address.DTO.class);
			return new Address(dto);
		}
	}
}
