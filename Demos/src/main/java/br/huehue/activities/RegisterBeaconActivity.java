package br.huehue.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.estimote.examples.demos.R;

public class RegisterBeaconActivity extends Activity {

	EditText edtMajor;
	EditText edtMinor;
	EditText riddle;
	EditText colour;
	private Spinner challengesSpn;
	private ArrayList<String> challengesArray;
	int major, minor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_beacon);

		edtMajor = (EditText) findViewById(R.id.edtMajor);
		edtMinor = (EditText) findViewById(R.id.edtMinor);

		Intent intent = getIntent();
		major = intent.getIntExtra("major", 0);
		minor = intent.getIntExtra("minor", 0);

		edtMajor.setText(String.valueOf(major));
		edtMinor.setText(String.valueOf(minor));

		edtMajor.setEnabled(false);
		edtMinor.setEnabled(false);

		challengesSpn = (Spinner) findViewById(R.id.spinnerChallenge);

		atualizaSpinner();

		challengesSpn.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				Log.d("Register Beacon", "Item Selecionado Spinner Pos: "
						+ String.valueOf(position + 1) + " de "
						+ challengesArray.size());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// DO nothing
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		atualizaSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_beacon, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void btnRegister(View view) {
		riddle = (EditText) findViewById(R.id.edtBeaconQuestion);
		colour = (EditText) findViewById(R.id.edtColor);

		// BeaconData beacon = DBAdapter.searchBeacon(minor, major);
		//
		// if (beacon == null)
		// beacon = DBAdapter.addBeacon(minor, major,
		// colour.getText().toString(), "Message");
		//
		// RiddleData riddleData = DBAdapter.addRiddle(beacon,
		// riddle.getText().toString());
		// List<FullStage> asdf = DBAdapter.getAllStages(dfsdafdsf.last);
		// DBAdapter.addStage(GlobalState.last, riddleData, asdf.size());

		Toast.makeText(getApplicationContext(),
				"This beacon was added to your challenge!", Toast.LENGTH_SHORT)
				.show();
		finish();
		return;

	}

	private void atualizaSpinner() {
		challengesArray = new ArrayList<String>();
		challengesArray.add("Challenge 1");
		challengesArray.add("Challenge 2");

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, challengesArray);

		challengesSpn.setAdapter(adapter1);
	}

}
