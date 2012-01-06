package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Fill implements RefBean {

	public enum EFill {
		None(-1), Solid(0);

		private int code;

		private EFill(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static EFill get(int code) {
			for(EFill fill: EFill.values()) {
				if (fill.getCode() == code) {
					return fill;
				}
			}
			return null;
		}
	}

	private String id;
	private EFill fill;
	private String color;

	protected Fill() {
		super();
	}

	@Override 
	public String getId() {
		return id;
	}
	@Override 
	public void setId(String id) {
		this.id = id;
	}
	public EFill getFill() {
		return fill;
	}
	public void setFill(EFill fill) {
		this.fill = fill;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
