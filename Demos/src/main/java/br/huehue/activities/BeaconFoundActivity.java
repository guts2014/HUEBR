package br.huehue.activities;

import com.estimote.examples.demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class BeaconFoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_beacon_found);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
