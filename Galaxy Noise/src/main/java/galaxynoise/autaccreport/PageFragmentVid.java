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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
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

    String PLATENUMBER= null;
    String CARMAKE= null;
    String CARMODEL= null;
    String CARYEAR = null;

    String DRIVERLICENSE = null, FNAME=null,LNAME = null, GENDER = null, INSURANCENUMBER = null;
    String reportid;
    //JSON
    private ProgressDialog pDialog;
    private String TAG = VidActivity.class.getSimpleName();

    //Arraylist
    ArrayList<HashMap<String, String>> carinfo = new ArrayList<>(3);
    ArrayList<HashMap<String, String>> driverinfo = new ArrayList<>(3);

    Button btnCarAdd;
    Button btnDriverAdd;


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
        reportid =myData[0];
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

            btnCarAdd = (Button)view.findViewById(R.id.btnCarAdd);

            btnCarAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executeAddCar();
                }
            });


            GetCar b = new GetCar();
            b.execute(reportid);


        }

        else if(mPage==2)//Driver
        {
            view= inflater.inflate(R.layout.fragment_driver,container, false);
            String reportid = myData[0];
            etLicense= (EditText) view.findViewById(R.id.etLicense);
            etFname = (EditText) view.findViewById(R.id.etFname);
            etLname= (EditText) view.findViewById(R.id.etLname);
            etGender = (EditText) view.findViewById(R.id.etGender);
            etInsurance = (EditText) view.findViewById(R.id.etInsurance);
            btnDriverAdd =(Button)view.findViewById(R.id.btnDriverAdd);

            btnDriverAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executeDriverAdd();
                }
            });

            GetDriver g = new GetDriver();
            g.execute(reportid);
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
                    double lati = 43.579028;
                    double longi = -79.746524;
                    LatLng event = new LatLng(lati,longi);
                    markerOptions = new MarkerOptions();

                    Log.d("tv: ","not set");
                    new ReverseGeoCodingTask(getContext()).execute(event);
                    //Log.d("tv: ",addressText);
                    String snippetAddress = tvReverseGeo.getText().toString();
                    googleMap.addMarker(markerOptions.position(event)
                            .title("Event Location")
                            .snippet(snippetAddress));

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
            String filepath = "android.resource://"+ myPackage+ "R.raw.testvideo";
            TextView tv= (TextView) view.findViewById(R.id.tvVideoPath);
            tv.setText(filepath     );
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

    void executeAddCar()
    {
        PLATENUMBER = etPN.getText().toString();
        CARMAKE = etBrand.getText().toString();
        CARMODEL = etBrand.getText().toString();
        CARYEAR = etYear.getText().toString();
        String addCarPHP ="addCar.php";
        AddCar a = new AddCar();
        a.execute(PLATENUMBER, CARMAKE, CARMODEL, CARYEAR,reportid, addCarPHP);
    }

    class AddCar extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String platenumber = params[0];
            String carmake = params[1];
            String carmodel = params[2];
            String caryear = params[3];
            String reportid = params[4];
            String data="";
            int tmp;

            try {
                URL url;
                if(params[5].equals("addCar.php")){
                    url = new URL("http://semjerome.com/app/"+params[5]);
                }
                else if(params[5].equals("updateCar.php"))
                {
                    url = new URL("http://semjerome.com/app/"+params[5]);
                }
                else
                {
                    url=new URL("http://semjerome.com/app/"+params[5]);
                }
                String urlParams = "platenumber="+platenumber+"&carmake="+carmake+"&carmodel="
                        +carmodel+"&caryear="+caryear+"&reportid="+reportid;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;

            try {
                JSONObject root = new JSONObject(s);
                //JSONObject user_data = root.getJSONObject("User");

                JSONObject car= root.getJSONObject("Car");

                car.put("platenumber", PLATENUMBER);
                car.put("carmake",CARMAKE);
                car.put("carmodel",CARMODEL);
                car.put("caryear",CARYEAR);
                car.put("reportid",reportid);

                Log.d("Storing to JSON plate: ", PLATENUMBER);
                Log.d("Storing to JSON make: ", CARMAKE);
                Log.d("Storing to JSON model: ", CARMODEL);


            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }
            if(PLATENUMBER!=null) {
                Toast.makeText(getActivity(), "Stored!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Fill in plate number", Toast.LENGTH_LONG).show();

            }
        }
    }

    //get Car
    class GetCar extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(String... params) {
            String reportid = params[0];
            HTTPHandler sh = new HTTPHandler();
            String data = "";
            int tmp;
            // Making a request to url and getting response
            //String uri = "http://semjerome.com/app/incident.php";
            //String jsonStr = sh.makeServiceCall(uri);


            try {
                URL url = new URL("http://semjerome.com/app/getCar.php");
                String urlParams = "reportid=" + reportid;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                //return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                // return "Exception: "+e.getMessage();
            }

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (data != null) {
                try {
                    JSONObject mainJsonObject = new JSONObject(data);
                    // Log.d("JSON Data : ", mainJsonObject.toString());


                    JSONArray mainArray = mainJsonObject.getJSONArray("Car");
                    // Log.d("JSON Array : ", mainArray.toString());

                    for (int i = 0; i < mainArray.length(); i++) {

                        JSONObject incidentJsonObject = mainArray.getJSONObject(i);

                        if (incidentJsonObject != null) {

                            String platenumber = incidentJsonObject
                                    .getString("platenumber");
                            String carmake = incidentJsonObject
                                    .getString("carmake");
                            String carmodel = incidentJsonObject
                                    .getString("carmodel");
                            String caryear = incidentJsonObject
                                    .getString("caryear");


                            HashMap<String, String> info = new HashMap<>();
                            info.put("platenumber", platenumber);
                            info.put("carmake", carmake);
                            info.put("carmodel", carmodel);
                            info.put("caryear", caryear);

                            // adding contact to contact list
                            carinfo.add(info);
                        }


                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            /**
             * Updating parsed JSON data into ListView
             **/
            if (carinfo.size() > 0) {
                etPN.setText(carinfo.get(0).get("platenumber").toString());
                etBrand.setText(carinfo.get(0).get("carmake").toString());
                etModel.setText(carinfo.get(0).get("carmodel").toString());
                etYear.setText(carinfo.get(0).get("caryear").toString());
            }
        }
        }

    void executeDriverAdd()
    {
        //DRIVERLICENSE = null, FNAME=null,LNAME = null, GENDER = null, INSURANCENUMBER = null;
        DRIVERLICENSE = etLicense.getText().toString();
        FNAME = etFname.getText().toString();
        LNAME = etLname.getText().toString();
        GENDER = etGender.getText().toString();
        INSURANCENUMBER = etInsurance.getText().toString();

        AddDriver a = new AddDriver();
        a.execute(DRIVERLICENSE, FNAME, FNAME, GENDER,INSURANCENUMBER,reportid);
    }

    //ADDING NEW DATA FOR DRIVER
    class AddDriver extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String driverlicense = params[0];
            String fname = params[1];
            String lname = params[2];
            String gender = params[3];
            String insurancenumber = params[4];
            String reportid = params[5];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://semjerome.com/app/addDriver.php");
                String urlParams = "driverlicense=" + driverlicense + "&fname=" + fname + "&lname="
                        + lname + "&gender=" + gender + "&insuranceNumber=" + insurancenumber + "&reportid=" + reportid;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            //Log.e(TAG, "Response from url: " + s);
            try {
                JSONObject root = new JSONObject(s);
                //JSONObject user_data = root.getJSONObject("User");
                JSONObject car = root.getJSONObject("Driver");

                car.put("driverlicense", DRIVERLICENSE);
                car.put("fname", FNAME);
                car.put("lname", LNAME);
                car.put("gender", GENDER);
                car.put("insuranceNumber", INSURANCENUMBER);
                car.put("reportid", reportid);

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            if (DRIVERLICENSE != null) {
                Log.d("Storing to JSON plate: ", DRIVERLICENSE);
                Log.d("Storing to JSON make: ", FNAME);
                Log.d("Storing to JSON model: ", LNAME);
                Toast.makeText(getActivity(), "Stored!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Fill in plate number", Toast.LENGTH_LONG).show();

            }
        }
    }
        //get Car
        class GetDriver extends AsyncTask<String, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog

            }

            @Override
            protected Void doInBackground(String... params) {
                String reportid = params[0];
                HTTPHandler sh = new HTTPHandler();
                String data = "";
                int tmp;
                // Making a request to url and getting response
                //String uri = "http://semjerome.com/app/incident.php";
                //String jsonStr = sh.makeServiceCall(uri);


                try {
                    URL url = new URL("http://semjerome.com/app/getDriver.php");
                    String urlParams = "reportid=" + reportid;

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    while ((tmp = is.read()) != -1) {
                        data += (char) tmp;
                    }

                    is.close();
                    httpURLConnection.disconnect();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //return "Exception: "+e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    // return "Exception: "+e.getMessage();
                }

                //Log.e(TAG, "Response from url: " + jsonStr);

                if (data != null) {
                    try {
                        JSONObject mainJsonObject = new JSONObject(data);
                        // Log.d("JSON Data : ", mainJsonObject.toString());


                        JSONArray mainArray = mainJsonObject.getJSONArray("Driver");
                        // Log.d("JSON Array : ", mainArray.toString());

                        for (int i = 0; i < mainArray.length(); i++) {

                            JSONObject incidentJsonObject = mainArray.getJSONObject(i);

                            if (incidentJsonObject != null) {

                                String driverlicense = incidentJsonObject
                                        .getString("driverlicense");
                                String fname = incidentJsonObject
                                        .getString("fname");
                                String lname = incidentJsonObject
                                        .getString("lname");
                                String gender = incidentJsonObject
                                        .getString("gender");
                                String insurancenumber = incidentJsonObject
                                        .getString("insuranceNumber");


                                HashMap<String, String> info = new HashMap<>();
                                info.put("driverlicense", driverlicense);
                                info.put("fname", fname);
                                info.put("lname", lname);
                                info.put("gender", gender);
                                info.put("insuranceNumber", insurancenumber);
                                // adding contact to contact list
                                driverinfo.add(info);
                            }


                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog

                /**
                 * Updating parsed JSON data into ListView
                 **/
                if (driverinfo.size() > 0) {
                    etLicense.setText(driverinfo.get(0).get("driverlicense").toString());
                    etFname.setText(driverinfo.get(0).get("fname").toString());
                    etLname.setText(driverinfo.get(0).get("lname").toString());
                    etGender.setText(driverinfo.get(0).get("gender").toString());
                    etInsurance.setText(driverinfo.get(0).get("insuranceNumber").toString());
                }
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