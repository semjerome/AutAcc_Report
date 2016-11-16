package galaxynoise.autaccreport;

import java.util.Date;

/**
 * Created by semjeromers on 11/7/2016.
 * Class object for Incident
 */

public class Incident {


    Date incidentDate;
    double longli;
    double lati;
    int reportId;
    /*

     */
    public Incident()
    {

    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public double getLongli() {
        return longli;
    }

    public void setLongli(int longli) {
        this.longli = longli;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(int lati) {
        this.lati = lati;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "reportId=" + reportId +
                ", incidentDate=" + incidentDate +
                '}';
    }
}
