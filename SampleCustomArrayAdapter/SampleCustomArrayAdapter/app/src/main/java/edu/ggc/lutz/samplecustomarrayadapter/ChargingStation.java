package edu.ggc.lutz.samplecustomarrayadapter;

public class ChargingStation {

    private final String nickName;
    private double neLat, neLon, swLat, swLon;
    private int available;
    private long time;
    private String stationName1, stationName2;

    public ChargingStation(double neLat, double neLon,
                           double swLat, double swLon,
                           String nickName, int available,

                           String name1, String name2) {
        this.neLat = neLat;
        this.neLon = neLon;
        this.swLat = swLat;
        this.swLon = swLon;
        this.nickName = nickName;
        this.time = System.currentTimeMillis();
        this.stationName1 = name1;
        this.stationName2 = name2;

        this.available = available;
    }

    public String getNickName() {return nickName;}
    public int getAvailable() {return available;}
    public long getTime() {return time;}
    public String getStationName1() {return stationName1;}
    public String getStationName2() {return stationName2;}

}
