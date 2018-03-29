package edu.ggc.lutz.ggcf3c;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.ggc.lutz.ggcf3c.chargepoint.ChargingStation;
import edu.ggc.lutz.ggcf3c.chargepoint.json.ChargePoint;
import edu.ggc.lutz.ggcf3c.chargepoint.json.StationList;
import edu.ggc.lutz.ggcf3c.chargepoint.json.Summary;

import static edu.ggc.lutz.ggcf3c.F3CActivity.gson;

public class CustomArrayAdapter extends ArrayAdapter<ChargingStation> {

    private final Activity context;
    private final List<ChargingStation> stations;

    public CustomArrayAdapter(Activity context, int resource,
                              List<ChargingStation> stations) {
        super(context, resource, stations);
        this.context = context;
        this.stations = stations;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.charge_station, null, true);

        String json = stations.get(position).getJson();
        ChargePoint chargepoint = gson.fromJson(json, ChargePoint.class);

        TextView txtNick = (TextView) rowView.findViewById(R.id.tvNick);
        TextView txtAvail = (TextView) rowView.findViewById(R.id.tvAvail);
        TextView txtTimestamp = (TextView) rowView.findViewById(R.id.tvTimestamp);
        TextView txtStationName1 = (TextView) rowView.findViewById(R.id.tvStationName1);
        TextView txtStationName2 = (TextView) rowView.findViewById(R.id.tvStationName2);

        StationList slist = chargepoint.getStationList();
        List<Summary> summaries = slist.getSummaries();
        Summary summary = summaries.get(0);

        long available = summary.getMapData().getLevel2().getPaid().getAvailable();
        txtNick.setText(stations.get(position).getNickName());
        txtNick.setTextColor(Color.BLACK);
        txtAvail.setText(available + " Available");
        txtAvail.setTextColor(available > 0 ? Color.GREEN : Color.BLACK);
        txtTimestamp.setText(chargepoint.getStationList().getTime());
        txtStationName1.setText(summary.getStationName().get(0));
        txtStationName2.setText(summary.getStationName().get(1));

        return rowView;

    };

}
