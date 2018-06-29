package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
public class Picture
{
	private DTO dto;

	public Picture(DTO dto)
	{
		this.dto = dto;
	}

	public String  getId()
	{
		return dto.id;
	}

	public String getUrl()
	{
		return dto.url;
	}

	public String getSecureUrl()
	{
		return dto.secure_url;
	}

	private static class DTO
	{
		private String id;
		private String url;
		private String secure_url;
	}

	public static class Deserializer implements JsonDeserializer<Picture>
	{
		@Override
		public Picture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Picture.DTO dto = context.deserialize(json, Picture.DTO.class);
			return new Picture(dto);
		}
	}
}
