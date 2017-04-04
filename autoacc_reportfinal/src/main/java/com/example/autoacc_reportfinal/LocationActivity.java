package com.example.autoacc_reportfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class LocationActivity extends Activity {
	    double latitude;
	    double longitude;
	    
	    GoogleMap googleMap;
		MarkerOptions markerOptions;
		LatLng latLng;
		TextView tAddress;
		String country;
		String locInfo;
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_location);
       // tAddress = (TextView) findViewById(R.id.textView1);
        	
        try {


			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();


			// Setting a click event handler for the map
			googleMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng arg0) {

					// Getting the Latitude and Longitude of the touched location
					latLng = arg0;

					// Clears the previously touched position
					googleMap.clear();

					// Animating to the touched position
					googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

					// Creating a marker
					markerOptions = new MarkerOptions();


					// Setting the position for the marker
					markerOptions.position(latLng);

					// Placing a marker on the touched position
					googleMap.addMarker(markerOptions);

					// Adding Marker on the touched location with address
					new ReverseGeocodingTask(getBaseContext()).execute(latLng);



				}
			});
		}
		catch (Exception ex){
			String a = ex.toString();
		}

    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String>{
		Context mContext;
		
		public ReverseGeocodingTask(Context context){
			super();
			mContext = context;
		}

		// Finding address using reverse geocoding
		@Override
		protected String doInBackground(LatLng... params) {
			Geocoder geocoder = new Geocoder(mContext);
			double latitude = params[0].latitude;
			double longitude = params[0].longitude;

			List<Address> addresses = null;
			String addressText="";


				if(addresses!= null && addresses.size() > 0 ) {
					// Parse the address
					Address address = addresses.get(0);
					addressText = String.format("%s, %s,%s,%s,", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
							address.getLocality(),
							address.getCountryName(),
							address.getPostalCode());
				}
			return addressText;
		}		
		
		@Override
		protected void onPostExecute(String addressText) {
			// Setting the title for the marker. 
			// This will be displayed on taping the marker
			
			
			// Placing a marker on the touched position
			googleMap.addMarker(markerOptions);
			
			markerOptions.title(addressText);
			markerOptions.snippet("Hello");
			
			tAddress.setText(addressText);

									
		}
	}
}
