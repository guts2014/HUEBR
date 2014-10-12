package br.huehue.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import br.huehue.database.ChallengeData;
import br.huehue.database.DBAdapter;

import com.estimote.examples.demos.R;


public class MainActivity extends Activity {

	public static Boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBAdapter.init(this);

		GlobalState.globalChallenge = new ArrayList<ChallengeData>();
		ChallengeData chData1 = DBAdapter.addChallenge("Challenge 1");
		ChallengeData chData2 = DBAdapter.addChallenge("Challenge 2");

		if (GlobalState.last == null)
			GlobalState.last = chData1;
		GlobalState.globalChallenge.add(chData1);
		GlobalState.globalChallenge.add(chData2);
		GlobalState.last = chData1;
		setContentView(R.layout.user_main);
		setTitle("Beacon Hunter");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!flag) {
			Intent intent = new Intent(MainActivity.this,
					BeaconsManagerActivity.class);
			startActivity(intent);
			flag = true;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		flag = false;
		super.onResume();
	}
}
