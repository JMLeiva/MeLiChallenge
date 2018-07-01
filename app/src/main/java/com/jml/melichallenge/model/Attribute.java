package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Attribute
{
	private DTO dto;

	public Attribute(DTO dto)
	{
		this.dto = dto;
	}

	public String getName()
	{
		return dto.name;
	}

	public String getValue()
	{
		return dto.value_name;
	}

	private static class DTO
	{
		private String id;
		private String name;
		private String value_name;
	}

	public static class Deserializer implements JsonDeserializer<Attribute>
	{
		@Override
		public Attribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Attribute.DTO dto = context.deserialize(json, Attribute.DTO.class);
			return new Attribute(dto);
		}
	}
}
