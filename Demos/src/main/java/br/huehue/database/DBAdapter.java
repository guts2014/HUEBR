package br.huehue.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.estimote.examples.demos.R;

/*
 * 
 * 
 * pra usar dentro de uma activity (uma vez so)
 * 
 *  DBAdapter.init(this);
 * 
 * 
 */

public class DBAdapter {
	/******* if debug is set true then it will show all Logcat message ***/
	public static final boolean DEBUG = true;

	/********** Logcat TAG ************/
	public static final String LOG_TAG = "DBAdapter";

	/************* Database Name ************/
	public static final String DATABASE_NAME = "DB_sqllite";

	/**** Database Version (Increase one if want to also upgrade your database) ****/
	public static final int DATABASE_VERSION = 4;// started at 1

	/**** Set all table with comma seperated like USER_TABLE,ABC_TABLE ******/
	private static final String[] ALL_TABLES = { "Beacon", "challenge",
		"Riddle", "Stage" };

	/********* Used to open database in syncronized way *********/
	private static DataBaseHelper DBHelper = null;

	protected DBAdapter() {

	}

	/********** Initialize database *********/
	public static void init(Context context) {
		if (DBHelper == null) {
			if (DEBUG)
				Log.i("DBAdapter", context.toString());
			DBHelper = new DataBaseHelper(context);
		}
	}

	/********** Main Database creation INNER class ********/
	private static class DataBaseHelper extends SQLiteOpenHelper {

		Context contx;

		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			contx = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.i(LOG_TAG, "new create");
			try {
				db.execSQL(contx.getResources().getString(R.string.dbstatement));

			} catch (Exception exception) {
				if (DEBUG)
					Log.i(LOG_TAG, "Exception111 onCreate() exception"
							+ exception.getMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (DEBUG)
				Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
						+ "to" + newVersion + "...");

			for (String table : ALL_TABLES) {
				db.execSQL("DROP TABLE IF EXISTS " + table);
			}
			onCreate(db);
		}

	} // Inner class closed

	/***** Open database for insert,update,delete in syncronized manner ****/
	protected static synchronized SQLiteDatabase open() throws SQLException {
		return DBHelper.getWritableDatabase();
	}

	/********* User data functons *********/

	public static ChallengeData addChallenge(String name) {
		return ChallengeTableAdapter.addChallenge(name);
	}

	public static BeaconData addBeacon(long minor, long major, String color,
			String guid) {

		return BeaconTableAdapter.addBeacon(minor, major, color, guid);
	}

	public static RiddleData addRiddle(BeaconData beaconData, String desc) {
		return RiddleTableAdapter.addRiddle(beaconData.getId_beacon(), desc);
	}

	public static StageData addStage(ChallengeData challengeData,
			RiddleData riddleData, long order) {
		return StageTableAdapter.addStage(challengeData.getId_challenge(),
				riddleData.getId_riddle(), order);
	}

	public static List<FullStage> getAllStages(ChallengeData challengeData) {
		List<FullStage> contactList = new ArrayList<FullStage>();
		// // Select All Query
		StringBuilder query = new StringBuilder();

		// "select stage.ord , riddle.descrip, beacon.minor , beacon.major,
		// beacon.color
		query.append(String.format(
				"select {0}.{1} , {2}.{3}, {4}.{5} , {4}.{6}, {4}.{7} ",
				StageTableAdapter.TABLE, StageTableAdapter.F_ORD,
				RiddleTableAdapter.TABLE, RiddleTableAdapter.F_DESCRIP,
				BeaconTableAdapter.TABLE, BeaconTableAdapter.F_MINOR,
				BeaconTableAdapter.F_MAJOR, BeaconTableAdapter.F_COLOR));

		// from stage join riddle join beacon on stage.id_riddle =
		// riddle.id_riddle and riddle.id_beacon = beacon.id_beacon
		query.append(String
				.format(" from {0} join {1} join {2} on {0}.{4} = {1}.{4} and {1}.{5} = {2}.{6} ",
						StageTableAdapter.TABLE, RiddleTableAdapter.TABLE,
						BeaconTableAdapter.TABLE,
						StageTableAdapter.F_ID_RIDDLE,
						RiddleTableAdapter.F_ID_RIDDLE,
						RiddleTableAdapter.F_ID_BEACON,
						BeaconTableAdapter.F_ID_BEACON));
		// where stage.id_challenge = 1
		query.append(String.format(" {0}.{1} = {2} ", StageTableAdapter.TABLE,
				StageTableAdapter.F_ID_CHALLENGE,
				String.valueOf(challengeData.getId_challenge())));

		// order by ord;
		query.append(String.format(" order by {0};", StageTableAdapter.F_ORD));

		String selectQuery = query.toString();

		// // Open database for Read / Write
		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);
		//
		// // looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				FullStage data = new FullStage();
				data.setOrd(Integer.parseInt(cursor.getString(0)));
				data.setDescrip(cursor.getString(1));
				data.setMinor(Integer.parseInt(cursor.getString(2)));
				data.setMajor(Integer.parseInt(cursor.getString(3)));
				data.setColor(cursor.getString(4));

				// Adding contact to list
				contactList.add(data);
			} while (cursor.moveToNext());
		}
		//
		// // return user list

		return contactList;
	}

	public static BeaconData searchBeacon(long minor, long major) {
		BeaconData beacondata = new BeaconData(0, 0, 0, "", "");
		// // Select All Query
		StringBuilder query = new StringBuilder();

		// "select stage.ord , riddle.descrip, beacon.minor , beacon.major,
		// beacon.color
		query.append(String.format(
				"select * from beacon where minor = {0} and major = {1} ",
				minor, major));

		String selectQuery = query.toString();

		// // Open database for Read / Write
		final SQLiteDatabase db = open();
		Cursor cursor = db.rawQuery(selectQuery, null);
		//
		// // looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				beacondata.setId_beacon(Integer.parseInt(cursor.getString(0)));
				beacondata.setMinor(Integer.parseInt(cursor.getString(1)));
				beacondata.setMajor(Integer.parseInt(cursor.getString(2)));
				beacondata.setColor(cursor.getString(3));
				beacondata.setGuid(cursor.getString(4));
			} while (cursor.moveToNext());
			return beacondata;
		} else
			return null;
		//
		// // return user list

	}

} // CLASS CLOSED