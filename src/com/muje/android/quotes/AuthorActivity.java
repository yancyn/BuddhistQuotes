package com.muje.android.quotes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AuthorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("DEBUG","Launching Author filter screen...");
		setContentView(R.layout.author);
	}

}
