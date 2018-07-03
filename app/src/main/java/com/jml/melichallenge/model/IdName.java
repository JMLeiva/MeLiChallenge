package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class IdName
{
	private DTO dto;

	IdName(DTO dto)
	{
		this.dto = dto;
	}

	IdName(IdName other)
	{
		this.dto = new DTO();
		this.dto.id = other.getId();
		this.dto.name = other.getName();
	}

	public String getName()
	{
		return dto.name;
	}

	public String getId()
	{
		return dto.id;
	}

	private static class DTO
	{
		private String id;
		private String name;
	}

	public static class Deserializer implements JsonDeserializer<IdName>
	{
		@Override
		public IdName deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			IdName.DTO dto = context.deserialize(json, IdName.DTO.class);
			return new IdName(dto);
		}
	}
}
