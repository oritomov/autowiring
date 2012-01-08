package com.google.code.autowiring;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
@SuppressWarnings("serial")
public class WiringException extends RuntimeException {

	public WiringException(final String message, final Exception e) {
		super(message, e);
	}

	public WiringException(final String message) {
		super(message);
	}

}
