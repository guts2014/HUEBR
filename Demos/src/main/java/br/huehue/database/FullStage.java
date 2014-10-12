package br.huehue.database;

public class FullStage {

	private long ord;
	private String descrip;
	private long minor;
	private long major;
	private String color;

	public FullStage() {

	}

	public FullStage(long ord, String descrip, long minor, long major,
			String color) {
		this.ord = ord;
		this.descrip = descrip;
		this.minor = minor;
		this.major = major;
		this.color = color;
	}

	public long getOrd() {
		return ord;
	}

	public void setOrd(long ord) {
		this.ord = ord;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public long getMinor() {
		return minor;
	}

	public void setMinor(long minor) {
		this.minor = minor;
	}

	public long getMajor() {
		return major;
	}

	public void setMajor(long major) {
		this.major = major;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
