package com.muje.android.quotes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParserException;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetTimer extends TimerTask {

	RemoteViews remoteViews;
	AppWidgetManager appWidgetManager;
	ComponentName widget;
	DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
	QuoteManager quoteManager;

	public WidgetTimer(Context context, AppWidgetManager appWidgetManager) {
			
		this.appWidgetManager = appWidgetManager;
		this.remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
		this.widget = new ComponentName(context, QuoteWidget.class);		
		this.quoteManager = new QuoteManager();
		try {
			this.quoteManager.extractQuotes(context);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Log.d("DEBUG", "WidgetTimer.run()");
		Quote quote = quoteManager.next();
		this.remoteViews.setTextViewText(R.id.textView1, quote.Text);//"Time = " + format.format(new Date())
		this.remoteViews.setTextViewText(R.id.textView2, "--"+quote.Source);
		//TODO: Set italic for source display or some style
		//TextView textView2 = context.f
		this.appWidgetManager.updateAppWidget(widget, remoteViews);
	}
}
