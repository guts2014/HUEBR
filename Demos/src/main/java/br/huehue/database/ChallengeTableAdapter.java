package br.huehue.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public final class ChallengeTableAdapter {

	public final static String TABLE = "Challenge";
	public final static String F_ID_CHALLENGE = "id_challenge";
	public final static String F_NAME = "name";

	public static ChallengeData addChallenge(String name) {

		ChallengeData data = new ChallengeData(0, name);

		return addChallenge(data);
	}

	public static ChallengeData addChallenge(ChallengeData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_NAME, data.getName());

		// Insert user values in database
		long id = db.insert(TABLE, null, cVal);
		db.close(); // Closing database connection

		data.setId_challenge(id);

		return (id != -1) ? data : null;
	}

	public int updateChallenge(long id_challenge, String name) {

		ChallengeData data = new ChallengeData(id_challenge, name);

		return updateChallenge(data);
	}

	public int updateChallenge(ChallengeData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_NAME, data.getName());

		// updating row
		return db.update(TABLE, cVal, F_ID_CHALLENGE + " = ?",
				new String[] { String.valueOf(data.getId_challenge()) });

	}

}
