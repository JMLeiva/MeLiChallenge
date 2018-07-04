package com.jml.melichallenge.model;

import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jml.melichallenge.R;
import com.jml.melichallenge.utils.CurrencySymbol;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

	public String getThumbnail()
	{
		return dto.thumbnail;
	}

	public int getPrice()
	{
		return dto.price;
	}

	public String getCurrencyId()
	{
		return dto.currency_id;
	}

	public boolean hasDiscount()
	{
		if(dto.original_price == null) return false;

		return dto.original_price > dto.price;
	}

	public Integer getOriginalPrice()
	{
		return dto.original_price;
	}

	public Integer getDiscount()
	{
		if(!hasDiscount())
		{
			return null;
		}

		return (int)(((float)(dto.original_price - dto.price) / dto.original_price) * 100);
	}

	public boolean hasEnoughtReviews()
	{
		// READ FROM CONFIG
		if(dto.reviews == null) return false;


		return dto.reviews.getTotal() > 0; // Customizable
	}

	public Reviews getReviews()
	{
		return dto.reviews;
	}

	public String getId()
	{
		return dto.id;
	}

	public boolean hasPictures()
	{
		return dto.pictures != null && dto.pictures.size() > 0;
	}

	public List<Picture> getPictures()
	{
		if(!hasPictures())
		{
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(dto.pictures);
	}

	public String getCondition()
	{
		return dto.condition;
	}

	public SellerAddress getSellerAddress()
	{
		return dto.seller_address;
	}

	public boolean hasAnyWarranty()
	{
		return  acceptsMercadoPago() || hasCustomWarranty();
	}

	public boolean acceptsMercadoPago()
	{
		return dto.accepts_mercadopago;
	}

	public boolean hasCustomWarranty()
	{
		return dto.warranty != null && !dto.warranty.isEmpty();
	}

	public String getCustomWarranty()
	{
		return dto.warranty;
	}

	public List<Attribute> getAttributes()
	{
		return dto.attributes;
	}

	private static class DTO
	{
		private String id;
		private String title;
		private int price;
		private Integer original_price;
		private String currency_id;
		private int available_quantity;
		private int sold_quantity;
		private String buying_mode;
		private String condition;
		private String permalink;
		private String thumbnail;
		private boolean accepts_mercadopago;
		private Address address;
		private SellerAddress seller_address;
		private String category_id;
		private Reviews reviews;
		private List<Attribute> attributes;
		private List<Picture> pictures;
		private String warranty;
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

	public static class FormatHelper
	{
		public static String formatPrice(Context context, String currecyCode, int price)
		{
			;
			NumberFormat currencyFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
			currencyFormatter.setMaximumFractionDigits(0);
			return CurrencySymbol.withCode(currecyCode) + currencyFormatter.format(price);
		}

		public static String formatDiscount(Context context, int discount)
		{
			return context.getString(R.string.discount_fmt, discount);
		}

		public static String formatReviewCount(Context context, int total)
		{
			return context.getString(R.string.reviewTotal_fmt, total);
		}
	}
}
