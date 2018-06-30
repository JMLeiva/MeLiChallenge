package com.jml.melichallenge.network;

import android.content.ClipData;

import com.google.gson.GsonBuilder;
import com.jml.melichallenge.model.Address;
import com.jml.melichallenge.model.Attribute;
import com.jml.melichallenge.model.IdName;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.Paging;
import com.jml.melichallenge.model.Picture;
import com.jml.melichallenge.model.Reviews;
import com.jml.melichallenge.model.SearchResult;
import com.jml.melichallenge.model.SellerAddress;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MeLiApiImpl implements MeLiApi
{
	private static final String URL_ROOT = "https://api.mercadolibre.com/";
	private static final String PATH_SITE = "sites";
	private static final String PATH_SEARCH = "search";
	private static final String PATH_ITEM = "items";

	private interface Service
	{
		@GET(PATH_SITE + "/{site_id}/" + PATH_SEARCH)
		Call<SearchResult> search(@Path("site_id") String siteId,
								  @Query("q") String qStr,
								  @Query("offset") int offset,
								  @Query("limit") int limit);


		@GET(PATH_ITEM + "/{item_id}")
		Call<Item> getItem(@Path("item_id") String itemId);
	}

	private Service service;

	public MeLiApiImpl()
	{
		this.service = createService();
	}

	private Service createService()
	{
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
				.addInterceptor(logging)
				.readTimeout(20, SECONDS)
				.connectTimeout(20, SECONDS);

		OkHttpClient okHttpClient = okHttpClientBuilder.build();

		GsonBuilder builder = new GsonBuilder()
				.registerTypeAdapter(Address.class, new Address.Deserializer())
				.registerTypeAdapter(Attribute.class, new Attribute.Deserializer())
				.registerTypeAdapter(Item.class, new Item.Deserializer())
				.registerTypeAdapter(Paging.class, new Paging.Deserializer())
				.registerTypeAdapter(Picture.class, new Picture.Deserializer())
				.registerTypeAdapter(Reviews.class, new Reviews.Deserializer())
				.registerTypeAdapter(SellerAddress.class, new SellerAddress.Deserializer())
				.registerTypeAdapter(IdName.class, new IdName.Deserializer())
				.registerTypeAdapter(SearchResult.class, new SearchResult.Deserializer());

		return new Retrofit.Builder()
				.baseUrl(URL_ROOT)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create(builder.create()))
				.build()
				.create(Service.class);
	}

	// API CALLS
	@Override
	public void search(String siteId, String qStr, int offset, int limit, final ApiCallback<SearchResult> callback)
	{
		service.search(siteId, qStr, offset, limit).enqueue(new Callback<SearchResult>()
		{
			@Override
			public void onResponse(Call<SearchResult> call, Response<SearchResult> response)
			{
				if(response.isSuccessful())
				{
					callback.onResponse(response.body());
				}
				else
				{
					String errorMessage = "";

					try
					{
						errorMessage = response.errorBody().string();
					}
					catch (IOException e)
					{
						// IGNORE
					}

					callback.onFailure(response.code(), errorMessage);
				}
			}

			@Override
			public void onFailure(Call<SearchResult> call, Throwable t)
			{
				callback.onFailure(0, t.getMessage());
			}
		});
	}

	@Override
	public void getItem(String itemId, final ApiCallback<Item> callback)
	{
		service.getItem(itemId).enqueue(new Callback<Item>()
		{
			@Override
			public void onResponse(Call<Item> call, Response<Item> response)
			{
				if(response.isSuccessful())
				{
					callback.onResponse(response.body());
				}
				else
				{
					String errorMessage = "";

					try
					{
						errorMessage = response.errorBody().string();
					}
					catch (IOException e)
					{
						// IGNORE
					}

					callback.onFailure(response.code(), errorMessage);
				}
			}

			@Override
			public void onFailure(Call<Item> call, Throwable t)
			{
				callback.onFailure(0, t.getMessage());
			}
		});
	}
}
