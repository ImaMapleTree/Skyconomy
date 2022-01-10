package me.imamapletree.extensions.exceptions;

@SuppressWarnings("serial")
public class ParentExistsException extends Exception {
	public ParentExistsException() {
		super("Parent doesn't exist!");
	}
}

