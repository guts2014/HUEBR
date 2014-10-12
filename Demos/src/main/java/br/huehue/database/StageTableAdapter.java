package br.huehue.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public final class StageTableAdapter {

	public final static String TABLE = "Stage";
	public final static String F_ID_STAGE = "id_stage";
	public final static String F_ID_CHALLENGE = "id_challenge";
	public final static String F_ID_RIDDLE = "id_riddle";
	public final static String F_ORD = "ord";

	public static StageData addStage(long id_challenge, long id_riddle, long ord) {

		StageData data = new StageData(0, id_challenge, id_riddle, ord);

		return addStage(data);
	}

	public static StageData addStage(StageData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_ID_CHALLENGE, data.getId_challenge());
		cVal.put(F_ID_RIDDLE, data.getId_riddle());
		cVal.put(F_ORD, data.getOrd());
		// Insert user values in database
		long id = db.insert(TABLE, null, cVal);
		db.close(); // Closing database connection

		data.setId_stage(id);

		return (id != -1) ? data : null;
	}

	public int updateStage(long id_stage, long id_challenge, long id_riddle,
			long ord) {

		StageData data = new StageData(id_stage, id_challenge, id_riddle, ord);

		return updateStage(data);
	}

	public int updateStage(StageData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_ID_CHALLENGE, data.getId_challenge());
		cVal.put(F_ID_RIDDLE, data.getId_riddle());
		cVal.put(F_ORD, data.getOrd());

		// updating row
		return db.update(TABLE, cVal, F_ID_STAGE + " = ?",
				new String[] { String.valueOf(data.getId_stage()) });
	}
}
