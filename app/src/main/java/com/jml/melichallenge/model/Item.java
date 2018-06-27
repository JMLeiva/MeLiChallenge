package com.jml.melichallenge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class Item
{
	private DTO dto;

	public Item(DTO dto)
	{
		this.dto = dto;
	}

	public String getTitle()
	{
		return dto.title;
	}

	private static class DTO
	{
		private String id;
		private String title;
		private int price;
		private String currency_id;
		private int available_quantity;
		private int sold_quantity;
		private String buying_mode;
		private String condition;
		private String permalink;
		private String thumbnail;
		private boolean accepts_mercadopago;
		private Address address;
		private String category_id;
		private Reviews reviews;
		private List<Attribute> attributes;
	}

	public static class Deserializer implements JsonDeserializer<Item>
	{
		@Override
		public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Item.DTO dto = context.deserialize(json, Item.DTO.class);
			return new Item(dto);
		}
	}
}
