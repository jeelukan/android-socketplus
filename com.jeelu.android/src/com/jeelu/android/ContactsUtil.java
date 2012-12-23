package com.jeelu.android;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.Contacts;
import android.provider.Contacts.PeopleColumns;
import android.provider.Contacts.PhonesColumns;
import android.provider.ContactsContract;

@SuppressWarnings("deprecation")
public class ContactsUtil
{
	private static final int REMOVE = 0;
	private static final int ADD = 1;
	Context context;

	public ContactsUtil(final Context context)
	{
		this.context = context;
	}

	public Boolean addKeepedContacts(final long id)
	{
		int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel > 7)
		{
			return addOrRemoveKeepedContactsInHighSDK(id, ContactsUtil.ADD);
		}
		else
		{
			return addOrRemoveKeepedContactsInLowSDK(id, ContactsUtil.ADD);
		}
	}

	public Boolean removeKeepedContacts(final long id)
	{
		int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel > 7)
		{
			return addOrRemoveKeepedContactsInHighSDK(id, ContactsUtil.REMOVE);
		}
		else
		{
			return addOrRemoveKeepedContactsInLowSDK(id, ContactsUtil.REMOVE);
		}
	}

	private Boolean addOrRemoveKeepedContactsInLowSDK(final long id, final int flag)
	{
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cusor = null;
		String[] projection = new String[] { BaseColumns._ID, PeopleColumns.NAME, PhonesColumns.NUMBER };
		cusor = contentResolver.query(Contacts.People.CONTENT_URI, projection, BaseColumns._ID + "=?", new String[] { id + "" }, PeopleColumns.NAME + " ASC");
		cusor.moveToFirst();
		ContentValues values = new ContentValues();
		Uri uri = Uri.withAppendedPath(Contacts.People.CONTENT_URI, cusor.getString(cusor.getColumnIndex(BaseColumns._ID)));
		// values.put(Contacts.People.NAME, newName);
		values.put(PeopleColumns.STARRED, flag);
		// values.put(Contacts.Phones.NUMBER, newPhoneNum);
		int i = contentResolver.update(uri, values, null, null);
		return i == 1;
	}

	private Boolean addOrRemoveKeepedContactsInHighSDK(final long id, final int flag)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(ContactsContract.Contacts.STARRED, flag);
		int i = context.getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, contentValues, BaseColumns._ID + " = ? ", new String[] { id + "" });
		return i == 1;
	}

	/**
	 * 根据ContactID得到电话号码
	 * 
	 * @param id
	 * @return
	 */
	public String getPhoneNumbersByContactID(final Long id)
	{
		Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + Long.toString(id), null, null);
		// 处理多个号码的情况
		String phoneNumber = "";
		while (cursor.moveToNext())
		{
			String strNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			phoneNumber += strNumber + ":";
		}
		cursor.close();
		return phoneNumber;
	}

	/**
	 * 根据电话号码得到ContactsID
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public Long getContactIDByPhoneNumber(final String phoneNumber)
	{
		Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.NUMBER + "=" + phoneNumber, null, null);
		Long ContactID = 0l;
		while (cursor.moveToNext())
		{
			ContactID = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
		}
		cursor.close();
		return ContactID;
	}

	/**
	 * 根据电话号码，该电话号码加入收藏中
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public Boolean addKeepedContactsByPhoneNumber(final String phoneNumber)
	{
		// 得到对应电话号码的contactID
		Long id = getContactIDByPhoneNumber(phoneNumber);

		return addKeepedContacts(id);
	}

	/**
	 * 根据电话号码，将该电话号码对应的联系人从收藏夹中移除
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public Boolean removeKeepedContactsByPhoneNumber(final String phoneNumber)
	{
		Long id = getContactIDByPhoneNumber(phoneNumber);
		return removeKeepedContacts(id);
	}
}