package com.chihan.LsshClassmate;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	Contact[] data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, Contact[] d, DBAdapter db) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return data.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ContactHolder holder = new ContactHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row, null);
			holder.name = (TextView) convertView.findViewById(R.id.name); // name
			holder.phonenumber = (TextView) convertView.findViewById(R.id.phonenumber); // phone// number
			holder.seatnumber = (TextView) convertView.findViewById(R.id.seatnumber); // seat// number
			holder.photo = (ImageView) convertView.findViewById(R.id.photo); // portrait
			holder.pick = (CheckBox) convertView.findViewById(R.id.checkBoxListRow);
			
			convertView.setTag(holder);
		} else {
			holder = (ContactHolder) convertView.getTag();
		}
		holder.contact = data[position];
		// Setting all values in listview
		holder.name.setText(holder.contact.getName());
		holder.phonenumber.setText(holder.contact.getPhone());
		holder.seatnumber.setText(holder.contact.getSeatNumber());
		holder.pick.setChecked(holder.contact.isPicked());
		holder.photo.setImageResource(holder.contact.getPhotoCode());
		holder.pick.setOnCheckedChangeListener((OnCheckedChangeListener) activity);

		return convertView;
	}

	private static class ContactHolder {
		public TextView name; // name
		public TextView phonenumber; // phone number
		public TextView seatnumber; // seat number
		public ImageView photo; // portrait
		public CheckBox pick;
		public Contact contact;
	}
}
