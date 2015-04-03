package com.muje.android.quotes;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class QuoteActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		Log.d("DEBUG", "launching quotes app...");
		setContentView(R.layout.app);
		
		//Resources resources = getResources();
		TabHost tabHost = getTabHost();
		
		Intent intent1 = new Intent(this, HomeActivity.class);
		TabSpec tab1 = tabHost.newTabSpec("Home")
				.setIndicator("Home")
				.setContent(intent1);
		
		Intent intent2 = new Intent(this, FavoriteActivity.class);
		TabSpec tab2 = tabHost.newTabSpec("Favorite")
				.setIndicator("Favorite")
				.setContent(intent2);
		
		Intent intent3 = new Intent(this, AuthorActivity.class);
		TabSpec tab3 = tabHost.newTabSpec("Author")
				.setIndicator("Author")
				.setContent(intent3);
		
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
	}

}
