package br.huehue.database;

public class StageData {
	private long id_stage;
	private long id_challenge;
	private long id_riddle;
	private long ord;

	public StageData(long id_stage, long id_challenge, long id_riddle, long ord) {
		this.id_stage = id_stage;
		this.id_challenge = id_challenge;
		this.id_riddle = id_riddle;
		this.ord = ord;
	}

	public long getId_stage() {
		return id_stage;
	}

	public void setId_stage(long id_stage) {
		this.id_stage = id_stage;
	}

	public long getId_challenge() {
		return id_challenge;
	}

	public void setId_challenge(long id_challenge) {
		this.id_challenge = id_challenge;
	}

	public long getId_riddle() {
		return id_riddle;
	}

	public void setId_riddle(long id_riddle) {
		this.id_riddle = id_riddle;
	}

	public long getOrd() {
		return ord;
	}

	public void setOrder(long ord) {
		this.ord = ord;
	}
}
