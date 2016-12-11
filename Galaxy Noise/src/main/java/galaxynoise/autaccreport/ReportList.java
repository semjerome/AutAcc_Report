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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

        int id =0;
        final Intent testIntent = new Intent(getApplicationContext(), VidActivity.class);
        /*Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            id = (int) b.get("SomeStringData");
        }
        User user = new User();
        user.setUid(id);
        View view;
        dbhandler = new DBHandler(getApplicationContext());*/
        listIncident=(ListView) findViewById(R.id.listAll);
      //  showIncidenttList(user);
        new GetInfo().execute();

    }
        private void showIncidenttList(User user) {
            ArrayList<Incident> incidentList = new ArrayList<Incident>();
            incidentList.clear();

            String query = "SELECT * FROM Incident WHERE uid="+ user.getUid();
            Cursor c1 = dbhandler.selectQuery(query);

            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {
                        Incident incident=new Incident();
                        int reportIndex = c1.getColumnIndex(dbhandler.COLUMN_REPORTID);
                        int incidateIndex = c1.getColumnIndex(dbhandler.COLUMN_INCIDENT_DATE);
                        int longiIndex = c1.getColumnIndex(dbhandler.COLUMN_LONGI);
                        int latiIndex = c1.getColumnIndex(dbhandler.COLUMN_LATI);
                        int videoIndex = c1.getColumnIndex(dbhandler.COLUMN_VIDEO_NAME);
                        incident.setReportId(c1.getInt(reportIndex));
                        incident.setIncidentDate(c1.getString(incidateIndex));
                        incident.setLongli(c1.getDouble(longiIndex));
                        incident.setLati(c1.getDouble(latiIndex));
                        incident.setVideoName(c1.getString(videoIndex));

                        incidentList.add(incident);

                    } while (c1.moveToNext());
                }
            }
            c1.close();
/*
            PatientAdapter patientAdapter=new PatientAdapter(getActivity(),patientList);
            listPat.setAdapter(patientAdapter);

*/

        }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HTTPHandler sh = new HTTPHandler();

            // Making a request to url and getting response
            String url = "http://semjerome.com/app/incident.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject mainJsonObject = new JSONObject(jsonStr);
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


