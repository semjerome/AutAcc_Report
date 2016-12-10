package galaxynoise.autaccreport;

import java.util.Date;

/**
 * Created by semjeromers on 11/7/2016.
 * Class object for Incident
 * Team name Galaxy Noise
 */

public class Incident {


    String incidentDate;
    double longi;
    double lati;
    int reportId;
    String videoName;
    /*

     */
    public Incident()
    {

    }
    public Incident(int reportId, String incidentDate, double longi, double lati, String videoName)
    {
        this.reportId = reportId;
        this.incidentDate=incidentDate;
        this.longi=longi;
        this.lati = lati;
        this.videoName = videoName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongli(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
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
