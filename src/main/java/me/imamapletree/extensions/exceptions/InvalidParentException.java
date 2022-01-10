package me.imamapletree.extensions.exceptions;

@SuppressWarnings("serial")
public class InvalidParentException extends Exception {
	public InvalidParentException(String reason) {
		super(reason);
	}
}

