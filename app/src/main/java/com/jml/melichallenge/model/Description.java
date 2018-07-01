package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Description
{
	private DTO dto;

	public Description(DTO dto)
	{
		this.dto = dto;
	}

	public String getText()
	{
		return dto.plain_text;
	}

	private static class DTO
	{
		private String plain_text;
	}

	public static class Deserializer implements JsonDeserializer<Description>
	{
		@Override
		public Description deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Description.DTO dto = context.deserialize(json, Description.DTO.class);
			return new Description(dto);
		}
	}
}
