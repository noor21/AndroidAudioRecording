package com.example.androidaudiorecording;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button next;
	EditText fname,lname,mbno,email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fname=(EditText)findViewById(R.id.fname);
		lname=(EditText)findViewById(R.id.lname);
		mbno=(EditText)findViewById(R.id.mbno);
		email=(EditText)findViewById(R.id.email_id);
		next=(Button)findViewById(R.id.next);
		
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Database db=new Database(getApplicationContext());
				db.open();
				long id;
				id=db.InsertTitles(fname.getText().toString(), lname.getText().toString(), mbno.getText().toString(), email.getText().toString());
				db.close();
				
				
				Toast.makeText(getBaseContext(), "Insertion Successfull", Toast.LENGTH_SHORT).show();
				
				startActivity(new Intent(getApplicationContext(),AudioRecordingActivity.class));
				
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
