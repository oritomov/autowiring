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
		Up(0), Down(1), Left(2), Right(3), UpMirror(4), DownMirror(5), LeftMirror(6), RightMirror(7);

		private int code;

		private Direction(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		// TODO fix matrix
		public String getTransform(double x, double y) {
			switch (this) {
				case Up:
					return "translate("+x+","+y+")";
				case Down:
					//return "matrix(-1,0,0,-1,"+x+","+y+")";
					return "translate("+x+","+y+")";
				case Left:
					//return "matrix(0,-1,1,0,"+x+","+y+")";
				case Right:
					return "matrix(0,1,1,0,"+x+","+y+")";
				case UpMirror:
					//return "matrix(-1,0,0,1,"+x+","+y+")";
				case DownMirror:
					//return "matrix(1,0,0,-1,"+x+","+y+")";
					return "translate("+x+","+y+")";
				case LeftMirror:
					//return "matrix(0,1,1,0,"+x+","+y+")";
				case RightMirror:
					//return "matrix(0,-1,-1,0,"+x+","+y+")";
					return "matrix(0,1,1,0,"+x+","+y+")";
				default:
					return null;
			}
		}
		public static Direction valueOf(int code) {
			for(Direction direction: Direction.values()) {
				if (direction.getCode() == code) {
					return direction;
				}
			}
			return null;
		}
	};

	public List<Bean> getBeans() throws WiringException;

	public void setBeans(List<Bean> defs, List<Bean> beans) throws WiringException;

	public void save() throws WiringException;
}
