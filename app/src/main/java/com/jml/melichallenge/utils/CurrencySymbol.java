package com.jml.melichallenge.utils;

import java.util.HashMap;
import java.util.Map;

/*
 * Utility class to show nice currency symbols, as the Currency java class can't handle "argentinian" Locale
 * */
public class CurrencySymbol
{
	private static Map<String, String> map = new HashMap<>();
	static
	{
		map.put("ARS", "$");
		map.put("USD", "U$D");

	}

	public static String withCode(String currencyCode)
	{
		if(map.containsKey(currencyCode)) return map.get(currencyCode);
		return currencyCode;
	}
}
