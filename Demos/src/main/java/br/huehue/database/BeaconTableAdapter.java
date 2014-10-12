package br.huehue.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public final class BeaconTableAdapter {

	public final static String TABLE = "Beacon";
	public final static String F_ID_BEACON = "id_beacon";
	public final static String F_MINOR = "minor";
	public final static String F_MAJOR = "major";
	public final static String F_COLOR = "color";
	public final static String F_GUID = "guid";

	public static BeaconData addBeacon(long minor, long major, String color,
			String guid) {
		BeaconData data = new BeaconData(0, minor, major, color, guid);
		return addBeacon(data);
	}

	public static BeaconData addBeacon(BeaconData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_MINOR, data.getMinor());
		cVal.put(F_MAJOR, data.getMajor());
		cVal.put(F_COLOR, data.getColor());
		cVal.put(F_GUID, data.getGuid());
		// Insert user values in database
		long id = db.insert(TABLE, null, cVal);
		db.close(); // Closing database connection

		data.setId_beacon(id);

		return (id != -1) ? data : null;
	}

	public int updateBeacon(long id_beacon, long minor, long major,
			String color, String guid) {

		BeaconData data = new BeaconData(id_beacon, minor, major, color, guid);

		return updateBecon(data);
	}

	public int updateBecon(BeaconData data) {

		final SQLiteDatabase db = DBAdapter.open();

		ContentValues cVal = new ContentValues();
		cVal.put(F_MINOR, data.getMinor());
		cVal.put(F_MAJOR, data.getMajor());
		cVal.put(F_COLOR, data.getColor());
		cVal.put(F_GUID, data.getGuid());

		// updating row
		return db.update(TABLE, cVal, F_ID_BEACON + " = ?",
				new String[] { String.valueOf(data.getId_beacon()) });

	}

}
