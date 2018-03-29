package edu.ggc.lutz.ggcf3c.chargepoint;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

import edu.ggc.lutz.ggcf3c.chargepoint.json.ChargePoint;
import edu.ggc.lutz.ggcf3c.chargepoint.json.Summary;

//import static edu.ggc.lutz.ggcf3c.F3CActivity.gson;

public class ChargingStation implements Comparable<ChargingStation> {
    private final String nickName;
    private String json;
    private Gson gson;

    private static final String BASE = "https://mc.chargepoint.com/map-prod/get";
    private double neLat, neLon, swLat, swLon;

    public ChargingStation(double neLat, double neLon, double swLat, double swLon, String nickName) {
        this.neLat = neLat; this.neLon = neLon;
        this.swLat = swLat; this.swLon = swLon;
        this.nickName = nickName;
        gson = new Gson();
    }

    public URL constructRESTfulURL() throws MalformedURLException {
        String u = BASE + "?{\"station_list\":{" +
        "\"ne_lat\":" + this.neLat + ",\"ne_lon\":" + this.neLon + "," +
        "\"sw_lat\":" + this.swLat + ",\"sw_lon\":" + this.swLon + "}}";
        return new URL(u);
    }

    public String getJson() {return json;}
    public void setJson(String json) {this.json = json;}
    public String getNickName() {return nickName;}

    @Override
    public int compareTo(ChargingStation cs2) {
        ChargingStation cs1 = this;
        ChargePoint cp1 = gson.fromJson(cs1.getJson(), ChargePoint.class);
        ChargePoint cp2 = gson.fromJson(cs2.getJson(), ChargePoint.class);

        Summary summary = null;
        summary = cp1.getStationList().getSummaries().get(0);
        long avail1 = summary.getMapData().getLevel2().getPaid().getAvailable();

        summary = cp2.getStationList().getSummaries().get(0);
        long avail2 = summary.getMapData().getLevel2().getPaid().getAvailable();

        if (avail1 < avail2)
            return -1;
        else if (avail1 > avail2)
            return 1;
        else
            return 0;
    }
}
