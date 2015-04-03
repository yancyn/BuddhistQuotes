package com.muje.android.quotes;

/**
 * Quote object carried source and quote text.
 * TODO: Will be extend the attribute in future.
 * @author yeang-shing.then
 * @since 2011-06-08
 */
public class Quote {
	/**
	 * Originator or source of this quote.
	 */
	public String Source;
	/**
	 * Content of the quote itself.
	 */
	public String Text;
	/**
	 * Default constructor.
	 * @param source Originator source where this quote from.
	 * @param text Quote content.
	 */
	public Quote(String source, String text) {
		this.Source = source;
		this.Text = text;
	}
}
