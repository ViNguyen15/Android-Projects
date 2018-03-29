
package edu.ggc.nchung.f3c.chargepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level2 {

    @SerializedName("paid")
    @Expose
    private Paid paid;

    public Paid getPaid() {
        return paid;
    }

    public void setPaid(Paid paid) {
        this.paid = paid;
    }

}
