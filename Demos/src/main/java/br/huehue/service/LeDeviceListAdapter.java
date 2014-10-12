package br.huehue.service;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.examples.demos.R;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;

/**
 * Displays basic information about beacon.
 *
 */
public class LeDeviceListAdapter extends BaseAdapter {

	private ArrayList<Beacon> beacons;
	private LayoutInflater inflater;

	public LeDeviceListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.beacons = new ArrayList<Beacon>();
	}

	public void replaceWith(Collection<Beacon> newBeacons) {
		this.beacons.clear();
		this.beacons.addAll(newBeacons);
		beacons = filterBeacons(beacons);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return beacons.size();
	}

	@Override
	public Beacon getItem(int position) {
		return beacons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflateIfRequired(view, position, parent);
		bind(getItem(position), view);
		return view;
	}

	private void bind(Beacon beacon, View view) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.macTextView.setText(String.format("Distance: %.2fm", Utils.computeAccuracy(beacon)));
		holder.majorTextView.setText("Major: " + beacon.getMajor());
		holder.minorTextView.setText("Minor: " + beacon.getMinor());
	}

	private View inflateIfRequired(View view, int position, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.device_list, null);
			view.setTag(new ViewHolder(view));
		}
		return view;
	}

	static class ViewHolder {
		final TextView macTextView;
		final TextView majorTextView;
		final TextView minorTextView;

		ViewHolder(View view) {
			macTextView = (TextView) view.findViewWithTag("mac");
			majorTextView = (TextView) view.findViewWithTag("major");
			minorTextView = (TextView) view.findViewWithTag("minor");
		}
	}

	private ArrayList<Beacon> filterBeacons(ArrayList<Beacon> beacons) {
		ArrayList<Beacon> aux = new ArrayList<Beacon>();
		aux.addAll(beacons);

		for (int i = 0; i < aux.size(); i++) {
			if (Utils.computeProximity(aux.get(i)) != Utils.Proximity.IMMEDIATE) {
				aux.remove(i);
				i--;
			}
		}

		return aux;

	}
}
