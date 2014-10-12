package br.huehue.database;

public class BeaconData {

	private long id_beacon;
	private long minor;
	private long major;
	private String color;
	private String guid;

	public BeaconData(long id_beacon, long minor, long major, String color,
			String guid) {
		this.id_beacon = id_beacon;
		this.minor = minor;
		this.major = major;
		this.color = color;
		this.guid = guid;
	}

	public void setId_beacon(long id_beacon) {
		this.id_beacon = id_beacon;
	}

	public long getId_beacon() {
		return id_beacon;
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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
