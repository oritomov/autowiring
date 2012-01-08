package com.google.code.autowiring;

import java.lang.reflect.Constructor;

import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class WiringFactory {

	public static Wiring createWiring(CfgEng engine, String fileName) {
		try {
			@SuppressWarnings("unchecked")
			Class<Wiring> wiringClass = (Class<Wiring>) Class.forName(engine.getClassName());
			Constructor<Wiring> wiringConstructor = wiringClass.getDeclaredConstructor(CfgEng.class, String.class);
			Wiring wiring = wiringConstructor.newInstance(engine, fileName);
			return wiring;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}
}
