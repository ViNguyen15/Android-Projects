
package edu.ggc.nchung.f3c.chargepoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("port_count")
    @Expose
    private PortCount portCount;
    @SerializedName("station_status")
    @Expose
    private String stationStatus;
    @SerializedName("map_data")
    @Expose
    private MapData mapData;
    @SerializedName("device_id")
    @Expose
    private long deviceId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("tou_status")
    @Expose
    private String touStatus;
    @SerializedName("can_remote_start_charge")
    @Expose
    private boolean canRemoteStartCharge;
    @SerializedName("is_connected")
    @Expose
    private boolean isConnected;
    @SerializedName("community_mode")
    @Expose
    private long communityMode;
    @SerializedName("distance")
    @Expose
    private long distance;
    @SerializedName("bearing")
    @Expose
    private long bearing;
    @SerializedName("port_type_count")
    @Expose
    private PortTypeCount portTypeCount;
    @SerializedName("station_name")
    @Expose
    private List<String> stationName = null;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pricing")
    @Expose
    private String pricing;
    @SerializedName("station_power_shed_status")
    @Expose
    private boolean stationPowerShedStatus;
    @SerializedName("tip_count")
    @Expose
    private long tipCount;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public PortCount getPortCount() {
        return portCount;
    }

    public void setPortCount(PortCount portCount) {
        this.portCount = portCount;
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus;
    }

    public MapData getMapData() {
        return mapData;
    }

    public void setMapData(MapData mapData) {
        this.mapData = mapData;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTouStatus() {
        return touStatus;
    }

    public void setTouStatus(String touStatus) {
        this.touStatus = touStatus;
    }

    public boolean isCanRemoteStartCharge() {
        return canRemoteStartCharge;
    }

    public void setCanRemoteStartCharge(boolean canRemoteStartCharge) {
        this.canRemoteStartCharge = canRemoteStartCharge;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public long getCommunityMode() {
        return communityMode;
    }

    public void setCommunityMode(long communityMode) {
        this.communityMode = communityMode;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getBearing() {
        return bearing;
    }

    public void setBearing(long bearing) {
        this.bearing = bearing;
    }

    public PortTypeCount getPortTypeCount() {
        return portTypeCount;
    }

    public void setPortTypeCount(PortTypeCount portTypeCount) {
        this.portTypeCount = portTypeCount;
    }

    public List<String> getStationName() {
        return stationName;
    }

    public void setStationName(List<String> stationName) {
        this.stationName = stationName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public boolean isStationPowerShedStatus() {
        return stationPowerShedStatus;
    }

    public void setStationPowerShedStatus(boolean stationPowerShedStatus) {
        this.stationPowerShedStatus = stationPowerShedStatus;
    }

    public long getTipCount() {
        return tipCount;
    }

    public void setTipCount(long tipCount) {
        this.tipCount = tipCount;
    }

}
