package com.muje.android.quotes;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Config page for widget
 * 
 * @author yeang-shing.then
 * @see http://android-er.blogspot.com/2010/10/simple-home-screen-app-widget-with.html
 */
public class QuoteWidgetConfig extends Activity {

	int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);
		setContentView(R.layout.config);

		Button button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(configOKButtonOnClickListener);
		
		Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
        	widgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, 
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        
        // If they gave us an intent without the widget id, just bail.
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
	}

	private OnClickListener configOKButtonOnClickListener = new OnClickListener() {

		public void onClick(View v) {

			EditText editText1 = (EditText) findViewById(R.id.editText1);
			RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
			RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
			RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
			
			long interval = 60 * 1000;// minimum one minute ticks
			int value = Integer.parseInt(editText1.getText().toString());
			if (radioButton1.isChecked()) // it is second
				interval =  value * 1000;
			if (radioButton2.isChecked()) // it is minute
				interval = value * 60*1000;
			if (radioButton3.isChecked()) // it is hour
				interval = value * 60*1000*60;			
			//QuoteWidget.INTERVAL = interval;
			
			final Context context = QuoteWidgetConfig.this;
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			QuoteWidget.updateAppWidget(context, appWidgetManager, widgetId, interval);
			
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
			setResult(RESULT_OK, resultValue);

			finish();
		}
	};
	
}
