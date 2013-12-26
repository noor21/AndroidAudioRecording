package com.example.androidaudiorecording;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioRecordingActivity extends Activity {
	
	Button start,stop,format,preview,rerecord;
	
	private String root="/mnt/sdcard/AudioRecorder";
	File file;
	
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";

	private MediaRecorder recorder = null;
	
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
			MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
			AUDIO_RECORDER_FILE_EXT_3GP };

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		enableButtons(false);
		/*if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED))
        {
		setButtonHandlers();
		
		setFormatButtonCaption();
        }
		else {
			Toast.makeText(getApplicationContext(), "NO SDCARD MOUNTED", 1).show();	
  
		}
*/	
		//setButtonHandlers();
		
		setFormatButtonCaption();
		
		start=(Button) findViewById(R.id.btnStart);
		start.setOnClickListener(btnClick);
		stop=(Button)findViewById(R.id.btnStop);
		stop.setOnClickListener(btnClick);
		format=(Button) findViewById(R.id.btnFormat);
		format.setOnClickListener(btnClick);
		preview=(Button) findViewById(R.id.preview);
		preview.setOnClickListener(btnClick);
		rerecord=(Button) findViewById(R.id.recordagain);
		rerecord.setOnClickListener(btnClick);
		
		
	}

	/*private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.preview)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.recordagain)).setOnClickListener(btnClick);
	}*/

	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
		enableButton(R.id.btnFormat, !isRecording);
		enableButton(R.id.btnStop, isRecording);
		enableButton(R.id.preview, isRecording);
		enableButton(R.id.recordagain, isRecording);
	}

	private void setFormatButtonCaption() {
		((Button) findViewById(R.id.btnFormat))
				.setText(getString(R.string.audio_format) + " ("
						+ file_exts[currentFormat] + ")");
	}

	private String getFilename() 
	{
		
		
			String filepath = Environment.getExternalStorageDirectory().getPath();
		 file = new File(filepath, AUDIO_RECORDER_FOLDER);
		int length=1;
		if (!file.exists()) 
		{
			file.mkdirs();
		}
		else
		{
			
			//length = file.listFiles().length==0?1:(file.listFiles().length+1);
			length = file.listFiles().length+1;
			
			Log.i("File Size: ", "---------"+length);
		}

		//return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
		//return (file.getAbsolutePath() + "/Voice" + length + file_exts[currentFormat]);
		return (file.getAbsolutePath() + "/Voice"  + file_exts[currentFormat]);
        
	}
		

	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			
			recorder.prepare();
			recorder.start();
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	private void stopRecording() 
	{
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;		 
			//startActivity(new Intent(getBaseContext(),RecordedData.class));
			
		}
		
		
	}

	private void displayFormatDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String formats[] = { "MPEG 4", "3GPP" };

		builder.setTitle(getString(R.string.choose_format_title))
				.setSingleChoiceItems(formats, currentFormat,
						new DialogInterface.OnClickListener() {

							
							public void onClick(DialogInterface dialog,
									int which) {
								currentFormat = which;
								setFormatButtonCaption();

								dialog.dismiss();
							}
						}).show();
	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AudioRecordingActivity.this,
					"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
	
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AudioRecordingActivity.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					.show();
		}
	};
	MediaPlayer m=null;
	private View.OnClickListener btnClick = new View.OnClickListener() {
		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				
				Toast.makeText(AudioRecordingActivity.this, "Start Recording",Toast.LENGTH_SHORT).show();
				
				//enableButtons(true);
				stop.setEnabled(true);
				start.setEnabled(false);
				format.setEnabled(false);
				
				startRecording();
				
				break;
			}
			case R.id.btnStop: {
				Toast.makeText(AudioRecordingActivity.this, "Stop Recording",Toast.LENGTH_SHORT).show();
				//enableButtons(false);
				preview.setEnabled(true);
				rerecord.setEnabled(true);
				stop.setEnabled(false);
				stopRecording();
				break;
			}
			case R.id.btnFormat: {
				displayFormatDialog();

				break;
			}
			
			case R.id.preview:{
				stop.setEnabled(false);
				format.setEnabled(true);
				
				m=new MediaPlayer();
				try {
					m.setDataSource(file.getAbsolutePath() + "/Voice"  + file_exts[currentFormat]);
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
			break;}
			case R.id.recordagain:{
				stop.setEnabled(true);
				format.setEnabled(false);
				start.setEnabled(false);
				preview.setEnabled(false);
				rerecord.setEnabled(false);
				
				//startRecording();
				if(m!=null)
				{				
				if(m.isPlaying())
				{
					m.stop();
					m.reset();
					m.release();					
					m=null;
				}}
				//enableButtons(true);
				startRecording();
				
				break;
			}
			
			
			}
		}
	};
}