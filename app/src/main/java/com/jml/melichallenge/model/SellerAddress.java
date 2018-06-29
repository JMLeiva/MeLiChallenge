package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class SellerAddress
{
	private DTO dto;

	public SellerAddress(DTO dto)
	{
		this.dto = dto;
	}

	private static class DTO
	{
		private IdName city;
		private IdName state;
		private IdName country;
	}

	public static class Deserializer implements JsonDeserializer<SellerAddress>
	{
		@Override
		public SellerAddress deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			SellerAddress.DTO dto = context.deserialize(json, SellerAddress.DTO.class);
			return new SellerAddress(dto);
		}
	}
}
