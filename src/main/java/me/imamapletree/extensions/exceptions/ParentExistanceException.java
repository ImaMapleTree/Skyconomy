package me.imamapletree.extensions.exceptions;

@SuppressWarnings("serial")
public class ParentExistanceException extends Exception {
	public ParentExistanceException() {
		super("Attempted to add a child to a non-existing parent.");
	}
}

