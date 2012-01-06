package com.google.code.autowiring;

import java.util.List;

import com.google.code.autowiring.WiringException;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public interface Wiring {

	public enum Direction {
		Up(0), Down(1), Left(2), Right(3);

		private int code;

		private Direction(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static Direction get(String code) {
			return get(Integer.parseInt(code));
		}
		public static Direction get(int code) {
			for(Direction direction: Direction.values()) {
				if (direction.getCode() == code) {
					return direction;
				}
			}
			return null;
		}
	};

	public List<Bean> getBeans() throws WiringException;

	public void setBeans(List<Bean> beans) throws WiringException;

	public void save() throws WiringException;
}
