
package edu.ggc.nchung.f3c.chargepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargePoint {

    @SerializedName("station_list")
    @Expose
    private StationList stationList;

    public StationList getStationList() {
        return stationList;
    }

    public void setStationList(StationList stationList) {
        this.stationList = stationList;
    }

}
