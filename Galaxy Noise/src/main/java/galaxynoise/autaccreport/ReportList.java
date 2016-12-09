package galaxynoise.autaccreport;

/**
 * planning on making it sliding tabs
 * With the incident list on first tab and then something lewse on secodn tab
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ReportList extends AppCompatActivity {

    private DBHandler dbhandler;
    ListView listPat;
    Intent currIntent;
    //PatientAdapter patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int id =0;
        final Intent testIntent = new Intent(getApplicationContext(), VidActivity.class);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            id = (int) b.get("SomeStringData");
        }
        User user = new User();
        user.setUid(id);
        View view;
        dbhandler = new DBHandler(getApplicationContext());
        listPat=(ListView) findViewById(R.id.listAll);
        showIncidenttList(user);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    }


