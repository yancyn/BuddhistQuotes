package com.muje.android.quotes;

import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * An activity for pop up a share via.
 * @author yeang-shing.then
 *
 */
public class PageActivity extends Activity {
	
	/**
	 * @see http://stackoverflow.com/questions/3481079/android-facebook-intent
	 * @see http://stackoverflow.com/questions/2953146/android-java-post-simple-text-to-facebook-wall
	 * @see http://forum.developers.facebook.net/viewtopic.php?id=93900
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page);		
		finish();
		
		//AlertDialog dialog = new AlertDialog.Builder(this).create();
		//dialog.setMessage("Share to");
		//dialog.show();
		
		Log.d("DEBUG","class: "+this.getIntent().getClass().getName());
		String subject = this.getIntent().getExtras().getString(android.content.Intent.EXTRA_SUBJECT);
		String message = this.getIntent().getExtras().getString(android.content.Intent.EXTRA_TEXT);
		
		/*
		 * @see http://androidthings.blogspot.com/2011/10/adding-share-button-to-your-android.html
		 * @see http://sudarmuthu.com/blog/sharing-content-in-android-using-action_send-intent
		 * @see http://blog.sephiroth.it/2010/09/18/android-create-your-own-sharing-app/
		 * @see http://android-developers.blogspot.com/2010/05/twitter-for-android-closer-look-at.html
		 */		
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");//"text/html"
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,message);
		startActivity(Intent.createChooser(shareIntent, "Share via"));
	}
}
