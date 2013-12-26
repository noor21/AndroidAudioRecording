package com.example.androidaudiorecording;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter 
{
	Context ctx;
	List<File> files;
	int layout;
	public CustomAdapter(List<File> filesData, int recordedFiles, Context context) 
	{
		files = filesData;
		ctx = context;
		layout = recordedFiles;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return files.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = null;
		if(view == null)
		{
			LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(layout,null);
		}
		((TextView)view.findViewById(R.id.textView1)).setText(""+files.get(position).getName());
		
		
		
		return view;
	}

}
