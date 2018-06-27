package com.jml.melichallenge.model;

public class SearchQuery
{
	private String site;
	private String qStr;
	private int limit;
	private int offset;

	public SearchQuery()
	{
		offset = 0;
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
