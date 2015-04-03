package com.muje.android.quotes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

//import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class QuoteManager {
	public ArrayList<Quote> Quotes = new ArrayList<Quote>();
	private Random random = new Random();
	/**
	 * Current selected index in collection.
	 */
	public int currentIndex;

	public QuoteManager() {
		
	}

	/**
	 * Extract xml into readable collection object.
	 * 
	 * @param activity
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see http://android-er.blogspot.com/2010/04/read-xml-resources-in-android-using.html
	 */
	public void extractQuotes(Context context)
			throws XmlPullParserException, IOException {
		
		int[] files = new int[]{R.xml.zhengyuan,R.xml.hsingyun,R.xml.shengyen};
		for(int i: files) {
			extractXml(context,i);
		}
	}
	/**
	 * Extract xml from resource file then added into quote collection.
	 * @param context
	 * @param resourceId
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws NotFoundException
	 */
	private void extractXml(Context context, int resourceId)
			throws XmlPullParserException, IOException, NotFoundException {
		
		Resources res = context.getResources();
		XmlResourceParser xpp = res.getXml(resourceId);
		xpp.next();
		
		int eventType = xpp.getEventType();
		String source = "";
		String content = "";
		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_TAG) {
				for (int i = 0; i < xpp.getAttributeCount(); i++) {
					String name = xpp.getAttributeName(i);
					if (name.compareTo("from") == 0)
						source = xpp.getAttributeValue(i);
				}
			}
			else if (eventType == XmlPullParser.TEXT)
				try {
					{
						content = xpp.getText();

						Quote quote = new Quote(source, content);
						this.Quotes.add(quote);

						source = "";// reset
						content = "";// reset
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			eventType = xpp.next();
		}		
	}
	
	/**
	 * Return a quote randomly.
	 * @return
	 */
	public Quote next() {
		if(this.Quotes.size() == 0) return new Quote("","");		
		this.currentIndex = random.nextInt(this.Quotes.size());//max is this.Quotes.size()-1
		Log.d("DEBUG","Next index: "+this.currentIndex);
		return this.Quotes.get(this.currentIndex);
	}
	/**
	 * TODO: Mark as favorite in database to display a red heart in interface.
	 * @see http://developer.android.com/training/basics/data-storage/databases.html
	 */
	public void toggleFavorite() {
		Log.d("DEBUG","Mark current index: "+ currentIndex + " as favorite");
		
		
	}
}
