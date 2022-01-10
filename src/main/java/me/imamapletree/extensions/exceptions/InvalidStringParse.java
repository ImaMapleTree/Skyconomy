package me.imamapletree.extensions.exceptions;

@SuppressWarnings("serial")
public class InvalidStringParse extends Exception {
	public InvalidStringParse(String reason) {
		super(reason);
	}
}

