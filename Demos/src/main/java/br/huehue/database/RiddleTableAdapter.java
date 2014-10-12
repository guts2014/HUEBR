package br.huehue.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public final class RiddleTableAdapter {

	public final static String TABLE = "Beacon";
	public final static String F_ID_RIDDLE = "id_riddle";
	public final static String F_ID_BEACON = "id_beacon";
	public final static String F_DESCRIP = "descrip";

	public static RiddleData addRiddle(long id_beacon, String descrip) {

		RiddleData data = new RiddleData(0, id_beacon, descrip);

		return addRiddle(data);
	}

	public static RiddleData addRiddle(RiddleData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_ID_BEACON, data.getId_beacon());
		cVal.put(F_DESCRIP, data.getDescrip());

		// Insert user values in database
		long id = db.insert(TABLE, null, cVal);
		db.close(); // Closing database connection

		data.setId_beacon(id);

		return (id != -1) ? data : null;
	}

	public int updateRiddle(long id_riddle, long id_beacon, String descrip) {

		RiddleData data = new RiddleData(id_riddle, id_beacon, descrip);

		return updateBecon(data);
	}

	public int updateBecon(RiddleData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_ID_BEACON, data.getId_beacon());
		cVal.put(F_DESCRIP, data.getDescrip());

		// updating row
		return db.update(TABLE, cVal, F_ID_RIDDLE + " = ?",
				new String[] { String.valueOf(data.getId_riddle()) });

	}

}
