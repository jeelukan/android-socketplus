package com.jeelu.android.socketplus.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.jeelu.android.Exceptions.SocketUnconnectedException;
import com.jeelu.android.Exceptions.SocketUninitializedException;
import com.jeelu.android.crazysocket.EasySocketClient;

public class EasySocketClientTest extends AndroidTestCase
{
	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		// TODO 自动生成的方法存根
	}

	/**
	 * 
	 * @throws Throwable
	 */
	public void testSend()
	{
		Log.d("SimpleSocketClientTest.testSend", "testSend开始");
		EasySocketClient client = new EasySocketClient("192.168.2.105", 5556);
		boolean result = false;
		try
		{
			client.BuildSocket();
			client.Send("testtestsetsetset");
			result = true;
		}
		catch (SocketUninitializedException e)
		{
			result = false;
		}
		catch (UnsupportedEncodingException e)
		{
			result = false;
		}
		catch (SocketUnconnectedException e)
		{
			result = false;
		}
		catch (IOException e)
		{
			result = false;
		}
		Assert.assertEquals(true, result);
		StringBuffer rev = new StringBuffer();
		result = false;
		try
		{
			client.Receive(rev);
			Log.d("SimpleSocketClientTest.testSend", "收到：" + rev.toString());
			result = true;
		}
		catch (SocketUninitializedException e)
		{
			result = false;
		}
		catch (SocketUnconnectedException e)
		{
			result = false;
		}
		catch (IOException e)
		{
			result = false;
		}
		Assert.assertEquals(true, result);
	}
}
