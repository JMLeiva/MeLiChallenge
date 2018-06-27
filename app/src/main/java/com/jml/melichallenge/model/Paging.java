package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Paging
{
	private DTO dto;

	public Paging(DTO dto)
	{
		this.dto = dto;
	}

	public int getTotal()
	{
		return dto.total;
	}

	public boolean isFinished()
	{
		return dto.offset + dto.limit >= dto.total;
	}


	private static class DTO
	{
		private int total;
		private int offset;
		private int limit;
		private int primary_results;
	}

	public static class Deserializer implements JsonDeserializer<Paging>
	{
		@Override
		public Paging deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Paging.DTO dto = context.deserialize(json, Paging.DTO.class);
			return new Paging(dto);
		}
	}
}
