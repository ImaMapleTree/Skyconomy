package me.imamapletree.extensions.exceptions;

@SuppressWarnings("serial")
public class NonIntegerableString extends Exception{
	public NonIntegerableString() {
		super("String cannot be converted to an integer.");
	}
}
