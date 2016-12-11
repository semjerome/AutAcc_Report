package galaxynoise.autaccreport;

/** //Team name Galaxy Noise
 * Created by Zaido on 2016-11-13.
 */
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class PageFragmentVid extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    EditText etPN;
    EditText  etBrand;
    EditText  etModel;
    EditText etYear;

    EditText etFname;
    EditText  etLname;
    EditText  etLicense;
    EditText etGender;
    EditText etInsurance;

    String [] myData;
    private int mPage;

    //JSON
    private ProgressDialog pDialog;
    private String TAG = VidActivity.class.getSimpleName();


    View view;
    // Identifier for the permission request
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

//hello
    public static PageFragmentVid create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentVid fragment = new PageFragmentVid();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    public void setCar()
    {

    }

    GoogleMap googleMap;
    MapView mMapView;
    MarkerOptions markerOptions;
    TextView tvReverseGeo;
    public String addressText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        VidActivity activity = (VidActivity) getActivity();
        myData = activity.getFromReport();
        /*
            0 = reportid , 1 = incidentdate, 2 = longi, 3 = lati, 4 = vidname
         */
        if(mPage==1) //Car
        {
            view = inflater.inflate(R.layout.fragment_car, container, false);
            etPN = (EditText) view.findViewById(R.id.etPN);
            etBrand= (EditText) view.findViewById(R.id.etBrand);
            etModel= (EditText) view.findViewById(R.id.etModel);
            etYear = (EditText) view.findViewById(R.id.etYear);

            etPN.setText(myData[0]);
        }

        else if(mPage==2)//Driver
        {
            view= inflater.inflate(R.layout.fragment_driver,container, false);
            etFname = (EditText) view.findViewById(R.id.etFname);
            etLname= (EditText) view.findViewById(R.id.etLname);
            etLicense= (EditText) view.findViewById(R.id.etLicense);
            etGender = (EditText) view.findViewById(R.id.etGender);
            etInsurance = (EditText) view.findViewById(R.id.etInsurance);
        }
        else if(mPage==3)
        { //location
            /*
            0 = reportid , 1 = incidentdate, 2 = longi, 3 = lati, 4 = vidname
         */

            view = inflater.inflate(R.layout.fragment_eventlocation, container, false);
            tvReverseGeo = (TextView)view.findViewById(R.id.tvReverseGeo);

            mMapView = (MapView) view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume();

            try{
                MapsInitializer.initialize(getActivity().getApplicationContext());
            }catch(Exception e){
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    if(checkPermission())
                    googleMap.setMyLocationEnabled(true);

                    //double longi = Double.parseDouble(myData[2]);
                    //double lati = Double.parseDouble(myData[3]);
                    //toronto
                    double lati = 43.65;
                    double longi = -78.57;
                    LatLng event = new LatLng(lati,longi);
                    markerOptions = new MarkerOptions();

                    Log.d("tv: ","not set");
                    new ReverseGeoCodingTask(getContext()).execute(event);
                    Log.d("tv: ",addressText);
                    googleMap.addMarker(markerOptions.position(event)
                            .title("Event Location")
                            .snippet(addressText));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(event).zoom(9).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                private boolean checkPermission()
                {
                    return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED);
                }

            });
        }
        else if (mPage == 4)
        { //video
            view = inflater.inflate(R.layout.fragment_eventvideos, container, false);
            //String videoUrl = "http://www.semjerome.com/Video_files/Family guy - archie take.3gp";
            //Uri uri = Uri.parse(videoUrl);

            VideoView mVideoView  = (VideoView) view.findViewById(R.id.videoView);
            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            String myPackage= "galaxynoise.autaccreport";
            Uri uri = Uri.parse("android.resource://" +myPackage+ "/" +R.raw.testvideo);
            try{
               mVideoView.setVideoURI(uri);
            }catch(Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            mVideoView.start();
            String filepath = "android.resource://"+ myPackage+ "R.raw.testvideo";
            TextView tv= (TextView) view.findViewById(R.id.tvVideoPath);
            tv.setText(filepath);
            //MediaController videoMediaController = new MediaController(this);
            //mVideoView.setVideoPath(mUrl);
            //videoMediaController.setMediaPlayer(mVideoView);
            //mVideoView.setMediaController(videoMediaController);
        }

        else
        {
            view = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) view;
            textView.setText("Fragment #" + mPage);
        }

        return view;
    }

    private class ReverseGeoCodingTask extends AsyncTask<LatLng, Void, String>{
        Context mContext;
        public ReverseGeoCodingTask(Context context){
            super();
            mContext = context;
        }

        @Override
        protected String doInBackground(LatLng... params){
            Geocoder geocoder  = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;

            List<Address> addresses = null;

            try {
            	addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
            	e.printStackTrace();
            }

            if(addresses!= null && addresses.size() > 0 ) {
                addressText = addresses.get(0).getAddressLine(0) +"," +
                        addresses.get(0).getLocality() +"," + addresses.get(0).getPostalCode() +
                        ", " +addresses.get(0).getCountryName();
            }

            return addressText;

        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            // Placing a marker on the touched position
            tvReverseGeo.setText(addressText);

        }

    }
    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI

        }
    }
}