package galaxynoise.autaccreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Team name Galaxy Noise
 * Created by semjeromers on 11/16/2016.
 */

public class IncidentAdapter extends BaseAdapter {

    Context context;
    ArrayList<Incident> incidentList;

    public IncidentAdapter(Context context, ArrayList<Incident> incidentList) {
        this.context = context;
        this.incidentList = incidentList;
    }

    @Override
    public int getCount() {
        return incidentList.size();
    }

    @Override
    public Object getItem(int position) {
        return incidentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Incident incident=incidentList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_report_list, null);

        }
        //TextView tvID= (TextView) convertView.findViewById(R.id.tvId);
        //tvID.setText((int) patient.getPatientId());
        TextView tvReport= (TextView) convertView.findViewById(R.id.tvreportName);
        tvReport.setText((int) incident.getReportId());
        return convertView;
    }
}
