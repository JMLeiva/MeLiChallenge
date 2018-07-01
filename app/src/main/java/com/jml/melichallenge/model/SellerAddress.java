package com.jml.melichallenge.model;

import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jml.melichallenge.R;

import java.lang.reflect.Type;

public class SellerAddress
{
	private DTO dto;

	public SellerAddress(DTO dto)
	{
		this.dto = dto;
	}

	public IdName getCity()
	{
		return dto.city;
	}

	public IdName getState()
	{
		return dto.state;
	}

	public IdName getCountry()
	{
		return dto.country;
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

	public static class FormatHelper
	{
		public static String format(Context context, SellerAddress sellerAddress)
		{
			return context.getString(R.string.location_fmt, sellerAddress.getCity().getName(), sellerAddress.getState().getName(), sellerAddress.getCountry().getName());
		}
	}
}
