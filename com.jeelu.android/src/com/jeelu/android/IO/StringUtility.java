package com.jeelu.android.IO;

import java.io.UnsupportedEncodingException;

public class StringUtility {


	public String FormatToGbkStr(final String str)
	{
		if ((str == null) || (str.length() == 0))
			return "";
		try
		{
			return new String(str.getBytes("UTF-8"), "ISO-8859-1");
		}
		catch (final UnsupportedEncodingException e)
		{
			return str;
		}
	}

	public String FormatToUTFStr(final String str)
	{
		if ((str == null) || (str.length() == 0))
			return "";
		try
		{
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			return str;
		}
	}
}
