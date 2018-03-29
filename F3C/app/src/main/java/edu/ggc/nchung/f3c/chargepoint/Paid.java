
package edu.ggc.nchung.f3c.chargepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paid {

    @SerializedName("available")
    @Expose
    private long available;
    @SerializedName("in_use")
    @Expose
    private long inUse;
    @SerializedName("total")
    @Expose
    private long total;

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public long getInUse() {
        return inUse;
    }

    public void setInUse(long inUse) {
        this.inUse = inUse;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
