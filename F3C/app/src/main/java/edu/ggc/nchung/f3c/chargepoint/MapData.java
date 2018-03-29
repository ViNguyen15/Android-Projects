
package edu.ggc.nchung.f3c.chargepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapData {

    @SerializedName("level2")
    @Expose
    private Level2 level2;

    public Level2 getLevel2() {
        return level2;
    }

    public void setLevel2(Level2 level2) {
        this.level2 = level2;
    }

}
