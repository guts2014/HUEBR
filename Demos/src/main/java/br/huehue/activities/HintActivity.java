package br.huehue.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.examples.demos.R;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

public class HintActivity extends Activity {

	private static final String TAG = HintActivity.class.getSimpleName();

	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON = "extrasBeacon";

	private static final int REQUEST_ENABLE_BT = 1234;
	private static final double range = 1;
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
			null, null, null);

	private ArrayList<Point> beaconsID = new ArrayList<Point>();
	private ArrayList<Boolean> beaconsFound = new ArrayList<Boolean>();
	private BeaconManager beaconManager;
	private TextView tip;
	private ArrayList<String> tipsText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_beacon_hint);

		initializeBeacon();

		tipsText = new ArrayList<String>();

		tipsText.add("JPMorgan");
		tipsText.add("Yo-Yo");
		tipsText.add("Chips Can");
		tip = (TextView) findViewById(R.id.textView2);
		tip.setText(tipsText.get(0));

		// Configure BeaconManager.
		beaconManager = new BeaconManager(this);
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {
				// Note that results are not delivered on UI thread.
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						filterBeacons(beacons);
					}
				});
			}
		});
	}

	private void initializeBeacon() {
		// Major Minor
		beaconsID.add(new Point(57469, 65535));
		beaconsID.add(new Point(35258, 14039));
		beaconsID.add(new Point(22802, 51128));

		beaconsFound.add(false);
		beaconsFound.add(false);
		beaconsFound.add(false);
	}

	private void filterBeacons(List<Beacon> beacons) {
		ArrayList<Beacon> aux = new ArrayList<Beacon>();
		aux.addAll(beacons);

		for (int i = 0; i < aux.size(); i++) {
			if (Utils.computeProximity(aux.get(i)) != Utils.Proximity.IMMEDIATE) {
				aux.remove(i);
				i--;
			}
		}

		// Looking for the first not found beacon
		int i;
		for (i = 0; i < beaconsFound.size(); i++) {
			if (!beaconsFound.get(i)) {
				break;
			}
		}

		// Checking to see if the game is over
		if (i == beaconsFound.size()) {
			Intent intent = new Intent(HintActivity.this, EndGameActivity.class);
			startActivity(intent);
			finish();
			return;
		}

		// testing to see if there is a beacon in immediate distance
		if (aux.size() > 0)
			// Checking to see if the beacon we are close is the one we are
			// looking for
			if (beaconsID.get(i).x == aux.get(0).getMajor()
					&& beaconsID.get(i).y == aux.get(0).getMinor()) {
				beaconsFound.set(i, true);
				Intent intent = new Intent(HintActivity.this,
						BeaconFoundActivity.class);
				startActivity(intent);
				// Updating tips
				if (i < beaconsFound.size() - 1) {
					tip.setText(tipsText.get(i + 1));
				}

			}

	}

	public void enteredInRange(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		beaconManager.disconnect();

		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Check if device supports Bluetooth Low Energy.
		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(this, "Device does not have Bluetooth Low Energy",
					Toast.LENGTH_LONG).show();
			return;
		}

		// If Bluetooth is not enabled, let user enable it.
		if (!beaconManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			connectToService();
		}
	}

	@Override
	protected void onStop() {
		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
		} catch (RemoteException e) {
			Log.d(TAG, "Error while stopping ranging", e);
		}

		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				connectToService();
			} else {
				Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG)
						.show();
				getActionBar().setSubtitle("Bluetooth not enabled");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void connectToService() {
		// getActionBar().setSubtitle("Scanning...");
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
				} catch (RemoteException e) {
					Toast.makeText(
							HintActivity.this,
							"Cannot start ranging, something terrible happened",
							Toast.LENGTH_LONG).show();
					Log.e(TAG, "Cannot start ranging", e);
				}
			}
		});
	}

	private AdapterView.OnItemClickListener createOnItemClickListener() {
		return new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (getIntent().getStringExtra(EXTRAS_TARGET_ACTIVITY) != null) {
					try {
						Class<?> clazz = Class.forName(getIntent()
								.getStringExtra(EXTRAS_TARGET_ACTIVITY));
						Intent intent = new Intent(HintActivity.this, clazz);
					} catch (ClassNotFoundException e) {
						Log.e(TAG, "Finding class by name failed", e);
					}
				}
			}
		};
	}

}
