package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

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

	public static class Deserializer implements JsonDeserializer<Reviews>
	{
		@Override
		public Reviews deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Reviews.DTO dto = context.deserialize(json, Reviews.DTO.class);
			return new Reviews(dto);
		}
	}
}
