package com.jeelu.android.socketplus.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.jeelu.android.Exceptions.SocketUnconnectedException;
import com.jeelu.android.Exceptions.SocketUninitializedException;
import com.jeelu.android.crazysocket.EasySocketClient;

public class EasySocketClientDemo
{
	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		String a = "";
		while (true)
		{
			System.out.println("输入A开始测试，输入X退出");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try
			{
				a = br.readLine();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			if (a == "X")
			{
				return;
			}
			if (a != "A")
			{
				continue;
			}
			// TODO 自动生成的方法存根
			Log.d("SimpleSocketClientTest.testSave", "testSave开始");
			EasySocketClient client = new EasySocketClient("192.168.2.91", 5554);
			try
			{
				String msg = "测试testtest";
				System.out.print("发送:" + msg);
				client.Send(msg);
				System.out.print("发送成功");
			}
			catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			catch (SocketUninitializedException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			catch (SocketUnconnectedException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			StringBuffer rev = new StringBuffer();
			try
			{
				client.Receive(rev);
			}
			catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			catch (SocketUninitializedException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			catch (SocketUnconnectedException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
