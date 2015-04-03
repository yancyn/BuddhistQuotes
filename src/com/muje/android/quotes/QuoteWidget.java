package com.muje.android.quotes;

import java.io.IOException;
import java.util.Timer;

import org.xmlpull.v1.XmlPullParserException;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
//import android.sax.StartElementListener;
import android.util.Log;
import android.widget.RemoteViews;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Widget class for Quote object.
 * 
 * @author yeang-shing.then
 * @see http://www.helloandroid.com/files/xmaswidget/android_howto-hellowidget.pdf
 * 
 * TODO: change to use service
 * @see http://www.vogella.de/articles/AndroidWidgets/article.html#overview_update
 */
public class QuoteWidget extends AppWidgetProvider {

	private static String NEXT_ACTION = "NEXT_ACTION";
	private static String SHARE_INTENT = "SHARE_INTENT";
	private static String MARK_FAVORITE = "MARK_FAVORITE";

	// must declared as static otherwise onReceive will always null again
	private static QuoteManager manager = new QuoteManager();
	private static Quote quote = null;

	// private TextView textViewNext;

	/**
	 * Interval time in milliseconds to change next new quote.
	 */
	// static long INTERVAL = 1000 * 60;// default

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		Log.d("DEBUG", "widget updating..");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		// mark as favorite
		Intent fav = new Intent(context, QuoteWidget.class);
		fav.setAction(MARK_FAVORITE);
		PendingIntent pi_fav = PendingIntent.getBroadcast(context, 0, fav, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.imageFavorite, pi_fav);
		
		// click content to share
		Intent share = new Intent(context,QuoteWidget.class);
		share.setAction(SHARE_INTENT);
		PendingIntent pi_share = PendingIntent.getBroadcast(context,0,share,0);//Intent.FLAG_ACTIVITY_NEW_TASK);//PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.imageShare,pi_share);
		
		// add onclick for the textViewNext to call onUpdate again
		Intent i = new Intent(context, QuoteWidget.class);
		i.setAction(NEXT_ACTION);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.imageNext, pi);
		
		updateQuote(context, views);
	
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		super.onReceive(context, intent);
		Log.d("DEBUG", intent.getAction());

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		if(intent.getAction().equals(MARK_FAVORITE)) {
			// TODO: Implement favorite
			manager.toggleFavorite();
			
		} else if (intent.getAction().equals(NEXT_ACTION)) {

			updateQuote(context, views);
			// @see http://advback.com/android/working-with-app-widgets-android/
			ComponentName cn = new ComponentName(context, QuoteWidget.class);
			AppWidgetManager.getInstance(context).updateAppWidget(cn, views);
		} else if (intent.getAction().equals(SHARE_INTENT)) {

			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setClassName("com.muje.android.quotes", "com.muje.android.quotes.PageActivity");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (QuoteWidget.quote != null) {
				String message = QuoteWidget.quote.Text + "--" + QuoteWidget.quote.Source;
				i.setType("text/plain");
				i.putExtra(android.content.Intent.EXTRA_SUBJECT,context.getString(R.string.app_name));
				i.putExtra(android.content.Intent.EXTRA_TEXT, message);
			} else {
				Log.d("DEBUG", "NULL QUOTE");
			}
			context.startActivity(i);
		}
	}
	
	/**
	 * Update quote randomly.
	 * @param context
	 * @param views
	 */
	private void updateQuote(Context context, RemoteViews views) {
		try {
			if (manager.Quotes.size() == 0) {
				Log.d("DEBUG", "quote size: 0");
				manager.extractQuotes(context);
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		QuoteWidget.quote = manager.next();
		Log.d("DEBUG", QuoteWidget.quote.Text);
		views.setTextViewText(R.id.textView1, QuoteWidget.quote.Text);
		views.setTextViewText(R.id.textView2, "--" + QuoteWidget.quote.Source);
		
		// TODO: manager.currentIndex
		//views.setImageViewResource(R.id.imageFavorite, R.drawable.favorite_red);
	}

	/**
	 * @deprecated
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 * @param interval
	 */
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, long interval) {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new WidgetTimer(context, appWidgetManager), 1, interval);
		Log.d("DEBUG", "QuoteWidget.onUpdate() with " + interval);
	}
}