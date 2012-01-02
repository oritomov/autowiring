package com.google.code.autowiring;

import java.lang.reflect.Constructor;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class WiringFactory {

	public static Wiring createWiring(String wiringName, String fileName) {
		try {
			@SuppressWarnings("unchecked")
			Class<Wiring> wiringClass = (Class<Wiring>) Class.forName(wiringName);
			Constructor<Wiring> wiringConstructor = wiringClass.getConstructor(String.class);
			Wiring wiring = wiringConstructor.newInstance(fileName);
			return wiring;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}
}
