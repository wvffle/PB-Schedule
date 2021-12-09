package net.wvffle.android.pb.schedule.api;

public class ScheduleUpdater {
    // NOTE: We're using http because https is misconfigured on the server
    private final static String XML_DB_URL = "http://degra.wi.pb.edu.pl/rozklady/webservices.php";

    public String getScheduleData() {
        return HTTPClient.get(XML_DB_URL);
    }

    public void updateScheduleData () {
        String xml = getScheduleData();
        System.out.println(xml);
    }
}
