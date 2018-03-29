package edu.ggc.lutz.samplecustomarrayadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<ChargingStation> {

    private final Context context;
    private final List<ChargingStation> stations;

    public CustomArrayAdapter(Context context, int resource,
                              List<ChargingStation> stations) {
        super(context, resource, stations);
        this.context = context;
        this.stations = stations;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView =inflater.inflate(R.layout.charge_station, null, true);

        TextView txtNick = itemView.findViewById(R.id.tvNick);
        TextView txtAvail = itemView.findViewById(R.id.tvAvail);
        TextView txtTimestamp = itemView.findViewById(R.id.tvTimestamp);
        TextView txtStationName1 = itemView.findViewById(R.id.tvStationName1);
        TextView txtStationName2 = itemView.findViewById(R.id.tvStationName2);

        ChargingStation station = stations.get(position);
        txtNick.setText(station.getNickName());
        txtNick.setTextColor(Color.BLACK);
        txtAvail.setText(station.getAvailable() + " Available");
        txtAvail.setTextColor(station.getAvailable() > 0 ? Color.GREEN : Color.BLACK);
        txtTimestamp.setText(new Date(station.getTime()).toString());
        txtStationName1.setText(station.getStationName1());
        txtStationName2.setText(station.getStationName2());

        return itemView;
    }
}
