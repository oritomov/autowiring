package com.google.code.autowiring;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class WiringException extends RuntimeException {

	public WiringException(String message, Exception e) {
		super(message, e);
	}

	public WiringException(String message) {
		super(message);
	}

}
