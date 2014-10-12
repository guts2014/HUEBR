package br.huehue.database;

public class RiddleData {

	private long id_riddle;
	private long id_beacon;
	private String descrip;

	public RiddleData(long id_riddle, long id_beacon, String descrip) {
		super();
		this.id_riddle = id_riddle;
		this.id_beacon = id_beacon;
		this.descrip = descrip;
	}

	public long getId_riddle() {
		return id_riddle;
	}

	public void setId_riddle(long id_riddle) {
		this.id_riddle = id_riddle;
	}

	public long getId_beacon() {
		return id_beacon;
	}

	public void setId_beacon(long id_beacon) {
		this.id_beacon = id_beacon;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

}
