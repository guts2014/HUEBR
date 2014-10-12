package br.huehue.activities;

import com.estimote.examples.demos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BeaconsManagerActivity extends Activity {

	private int touchCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_beacons_manager);

		findViewById(R.id.cha1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BeaconsManagerActivity.this,
						HintActivity.class);
				intent.putExtra(HintActivity.EXTRAS_TARGET_ACTIVITY,
						HintActivity.class.getName());
				startActivity(intent);
			}
		});

		findViewById(R.id.set).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				touchCounter++;

				if (touchCounter == 7) {
					Toast.makeText(getApplicationContext(), "Welcome admin!",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(BeaconsManagerActivity.this,
							SettingsActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		touchCounter = 0;
	}
}
