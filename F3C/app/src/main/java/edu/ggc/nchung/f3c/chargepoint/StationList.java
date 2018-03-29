
package edu.ggc.nchung.f3c.chargepoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationList {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("watermark")
    @Expose
    private long watermark;
    @SerializedName("map_data_size")
    @Expose
    private long mapDataSize;
    @SerializedName("summaries")
    @Expose
    private List<Summary> summaries = null;
    @SerializedName("port_type_info")
    @Expose
    private PortTypeInfo portTypeInfo;
    @SerializedName("port_type_text_map")
    @Expose
    private PortTypeTextMap portTypeTextMap;
    @SerializedName("page_offset")
    @Expose
    private String pageOffset;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getWatermark() {
        return watermark;
    }

    public void setWatermark(long watermark) {
        this.watermark = watermark;
    }

    public long getMapDataSize() {
        return mapDataSize;
    }

    public void setMapDataSize(long mapDataSize) {
        this.mapDataSize = mapDataSize;
    }

    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }

    public PortTypeInfo getPortTypeInfo() {
        return portTypeInfo;
    }

    public void setPortTypeInfo(PortTypeInfo portTypeInfo) {
        this.portTypeInfo = portTypeInfo;
    }

    public PortTypeTextMap getPortTypeTextMap() {
        return portTypeTextMap;
    }

    public void setPortTypeTextMap(PortTypeTextMap portTypeTextMap) {
        this.portTypeTextMap = portTypeTextMap;
    }

    public String getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(String pageOffset) {
        this.pageOffset = pageOffset;
    }

}
