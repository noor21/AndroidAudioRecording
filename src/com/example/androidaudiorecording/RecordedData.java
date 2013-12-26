package com.example.androidaudiorecording;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class RecordedData extends ListActivity 
{
	List<File> files_data = new ArrayList<File>();
	String path = Environment.getExternalStorageState();
	//private String root="/mnt/sdcard/AudioRecorder";
	 String root=Environment.getExternalStorageDirectory().getPath()+File.separator+"AudioRecorder";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.recorded_files);
		//Log.i("-----", root+"\n"+Environment.getExternalStorageDirectory()+"\n"+Environment.getRootDirectory());
		Log.i("-----", root);
		File file_path = new File(root);
		File[] audio_files = file_path.listFiles();
		for (int i = 0; i < audio_files.length; i++)
		{
			Log.i("File "+(i+1), audio_files[i].getName());
			files_data.add(audio_files[i]);			
		}
		ListView lv=getListView();
		lv.setAdapter(new CustomAdapter(files_data,R.layout.recorded_files,getBaseContext()));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				MediaPlayer m=new MediaPlayer();
				try {
					m.setDataSource(root+'/'+files_data.get(arg2).getName());
					//m.seekTo(0);
					m.prepare();
					m.start();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
								
			}
		});
			
	}
	
}
