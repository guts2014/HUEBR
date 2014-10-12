package br.huehue.database;

public class ChallengeData {

	private long id_challenge;

	private String name;

	public ChallengeData(long id_challenge, String name) {
		this.id_challenge = id_challenge;
		this.name = name;
	}

	public long getId_challenge() {
		return id_challenge;
	}

	public void setId_challenge(long id_challenge) {
		this.id_challenge = id_challenge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
