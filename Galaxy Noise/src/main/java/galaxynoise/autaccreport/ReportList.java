package galaxynoise.autaccreport;

/**
 * planning on making it sliding tabs
 * With the incident list on first tab and then something lewse on secodn tab
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;

public class ReportList extends AppCompatActivity {

    private DBHandler dbhandler;
    ListView listIncident;
    Intent currIntent;
    //PatientAdapter patientAdapter;
    ArrayList<HashMap<String, String>> infolist = new ArrayList<>(3);
    private String TAG = ReportList.class.getSimpleName();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id=null, username ;
        final Intent testIntent = new Intent(getApplicationContext(), VidActivity.class);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            id =  (String)b.get("uid");
            username =  (String)b.get("name");
        }
        /**
        User user = new User();
        user.setUid(id);
        View view;
        dbhandler = new DBHandler(getApplicationContext());*/
        listIncident=(ListView) findViewById(R.id.listAll);
      //  showIncidenttList(user);
        GetInfo info = new GetInfo();
        info.execute(id);
        listIncident.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                Toast.makeText(ReportList.this, "Selected!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
     class GetInfo extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(String... params) {
            String uid = params[0];
            HTTPHandler sh = new HTTPHandler();
            String data="";
            int tmp;
            // Making a request to url and getting response
            //String uri = "http://semjerome.com/app/incident.php";
            //String jsonStr = sh.makeServiceCall(uri);


            try {
                URL url = new URL("http://semjerome.com/app/incident.php");
                String urlParams = "uid="+uid;

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

                    infolist.clear();
                    JSONArray mainArray = mainJsonObject.getJSONArray("Incident");
                    // Log.d("JSON Array : ", mainArray.toString());

                    for (int i = 0; i < mainArray.length(); i++) {

                        JSONObject incidentJsonObject = mainArray.getJSONObject(i);



                                if (incidentJsonObject != null) {

                                    String reportid = incidentJsonObject
                                            .getString("reportid");
                                    Log.d("Inside JSON LongName : ", reportid);
                                    String incidentdate = incidentJsonObject
                                            .getString("incidentdate");
                                    String longi = incidentJsonObject
                                            .getString("longi");
                                    String lati = incidentJsonObject
                                            .getString("lati");
                                    String videoName = incidentJsonObject
                                            .getString("videoName");

                                    HashMap<String, String> info = new HashMap<>();
                                    info.put("reportid", reportid);
                                    info.put("incidentdate", incidentdate);
                                    info.put("longi", longi);
                                    info.put("lati", lati);
                                    info.put("videoName", videoName);

                                    // adding contact to contact list
                                    infolist.add(info);




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
        protected void onPostExecute (Void result){
            super.onPostExecute(result);
            // Dismiss the progress dialog

            /**
             * Updating parsed JSON data into ListView
             **/
             ListAdapter adapter = new SimpleAdapter(
             ReportList.this, infolist,
             R.layout.content_report_list, new String[]{"reportid", "incidentdate"}, new int[]{R.id.tvreportName,
             R.id.tvdate});

             listIncident.setAdapter(adapter);

        }

    }

    }


