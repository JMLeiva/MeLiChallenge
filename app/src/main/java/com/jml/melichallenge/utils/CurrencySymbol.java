package com.jml.melichallenge.utils;

import java.util.HashMap;
import java.util.Map;

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
