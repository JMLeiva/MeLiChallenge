package com.jml.melichallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
public class Picture implements Parcelable
{
	private DTO dto;

	public Picture(DTO dto)
	{
		this.dto = dto;
	}

	private Picture(Parcel in) {
		this.dto = new DTO();
		this.dto.id = in.readString();
		this.dto.url = in.readString();
		this.dto.secure_url = in.readString();
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

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.dto.id);
		dest.writeString(this.dto.url);
		dest.writeString(this.dto.secure_url);
	}

	public static final Parcelable.Creator<Picture> CREATOR
			= new Parcelable.Creator<Picture>() {
		public Picture createFromParcel(Parcel in) {
			return new Picture(in);
		}

		public Picture[] newArray(int size) {
			return new Picture[size];
		}
	};

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
