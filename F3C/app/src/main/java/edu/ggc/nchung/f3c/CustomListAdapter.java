package edu.ggc.nchung.f3c;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.ggc.nchung.f3c.chargepoint.ChargePoint;
import edu.ggc.nchung.f3c.chargepoint.StationList;
import edu.ggc.nchung.f3c.chargepoint.Summary;

/** Apply created Custom Array Adapter of custom_list.xml **/
public class CustomListAdapter extends ArrayAdapter<ChargingStation> {

    private final Activity context;
    private int resource;
    private ArrayList<ChargingStation> stationResults;
    private Gson gson;
    private TextToSpeech tts;

    // Create Constructors - Default conventions/parameters; Modified 3rd parameter
    public CustomListAdapter(Activity context, int resource, ArrayList<ChargingStation> stationResults) {
        super(context, resource, stationResults);
        this.context = context;
        this.resource = resource;
        this.stationResults = stationResults;
        gson = new Gson();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // Inflate context of 4 TextViews into one TV - each rowView acts as separate outputs per stationResults
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list, null,true);

        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        // Title Names; Descriptions of each Names; Time; Available Status
        TextView tvMainTitle = (TextView) rowView.findViewById(R.id.tvMainNames);
        TextView tvSubNames = (TextView) rowView.findViewById(R.id.tvSubNames);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tvTime);
        TextView tvAvailableStatus = (TextView) rowView.findViewById(R.id.tvAvailable);

        // Traverse through JSON objects - from Dr.Lutz's Reference helper
        ChargePoint chargepoint = gson.fromJson(stationResults.get(position).getJson(), ChargePoint.class);
        StationList stationlist = chargepoint.getStationList();
        List<Summary> summaries = stationlist.getSummaries();
        final Summary summary = summaries.get(0);
        final long available = summary.getMapData().getLevel2().getPaid().getAvailable();

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = available + "! " +
                        ((available == 1) ? "charger is" : "chargers are") +
                        " available." + "This location is " + summary.getDescription();
                Toast.makeText(context.getApplicationContext(), toSpeak.replace("!", ""), Toast.LENGTH_LONG).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        tvMainTitle.setText(stationResults.get(position).getNickName());
        tvSubNames.setText(summary.getStationName().get(0) + " / " + summary.getStationName().get(1));
        tvTime.setText(chargepoint.getStationList().getTime());
        tvAvailableStatus.setText(available + " Available");

        if (available > 0){
            tvAvailableStatus.setTextColor(Color.GREEN);
        }
        else tvAvailableStatus.setTextColor(Color.BLACK);

        return rowView;
    }
}


    /** Old code that works with display TextView **/
    /*
    public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] listNames;
    private Gson gson;
    private ArrayList<ChargingStation> stations;

      public CustomListAdapter(Activity context, String[] listNames) {
        super(context, R.layout.custom_list, listNames);
        this.context = context;
        this.listNames = listNames;
          gson = new Gson();
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list, null, true);

        TextView tvMainTitle = (TextView) rowView.findViewById(R.id.tvMainNames);
        TextView tvSubNames = (TextView) rowView.findViewById(R.id.tvSubNames);
        TextView tvAvailableStatus = (TextView) rowView.findViewById(R.id.tvAvailable);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tvTime);

        tvMainTitle.setText(listNames[position]);
        tvSubNames.setText("GA Gwinnett Col / ");
        tvAvailableStatus.setText(" available");
        tvTime.setText("" + new Date());
        return rowView;
    }
}
*/
