package com.jml.melichallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchQuery implements Parcelable
{
	public static final String DEFAULT_SITE = "MLA";
	public static final int DEFAILT_PAGE_SIZE = 10;

	private String site;
	private String qStr;
	private int limit;
	private int offset;
	private String sort;

	public SearchQuery()
	{
		offset = 0;
	}

	protected SearchQuery(Parcel in)
	{
		site = in.readString();
		qStr = in.readString();
		limit = in.readInt();
		offset = in.readInt();
		sort = in.readString();
	}

	public String getQStr()
	{
		return qStr;
	}

	public String getSite()
	{
		return site;
	}

	public int getLimit()
	{
		return limit;
	}

	public int getOffset()
	{
		return offset;
	}

	public void advancePage()
	{
		this.offset += limit;
	}

	public void resetPaging()
	{
		this.offset = 0;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(site);
		dest.writeString(qStr);
		dest.writeInt(limit);
		dest.writeInt(offset);
		dest.writeString(sort);
	}

	public static final Creator<SearchQuery> CREATOR = new Creator<SearchQuery>()
	{
		@Override
		public SearchQuery createFromParcel(Parcel in)
		{
			return new SearchQuery(in);
		}

		@Override
		public SearchQuery[] newArray(int size)
		{
			return new SearchQuery[size];
		}
	};

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public void setSite(String site)
	{
		this.site = site;
	}

	public void setQStr(String QStr)
	{
		this.qStr = QStr;
	}

	public static class Builder
	{
		SearchQuery builderSearchQuuery;

		public Builder()
		{
			builderSearchQuuery = new SearchQuery();
		}

		public Builder site(String site)
		{
			this.builderSearchQuuery.site = site;
			return this;
		}

		public Builder qStr(String qStr)
		{
			builderSearchQuuery.qStr = qStr;
			return this;
		}

		public Builder pageSize(int pageSize)
		{
			builderSearchQuuery.limit = pageSize;
			return this;
		}

		public SearchQuery build()
		{
			SearchQuery result = new SearchQuery();
			result.site = builderSearchQuuery.site;
			result.qStr = builderSearchQuuery.qStr;
			result.limit = builderSearchQuuery.limit;

			return result;
		}
	}
}
