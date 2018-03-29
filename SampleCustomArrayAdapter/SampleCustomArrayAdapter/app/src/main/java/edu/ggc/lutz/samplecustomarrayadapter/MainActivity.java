package edu.ggc.lutz.samplecustomarrayadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ChargingStation> stations;
    private CustomArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRemove = findViewById(R.id.btnRemove);

        stations = new ArrayList<ChargingStation>();
        stations.add(new ChargingStation(10, 10,10,10,
                "B", 2, "GA GWINNETT COL", "Building B Lot"));
        stations.add(new ChargingStation(20, 20,20,20,
                "Deck", 1, "GA GWINNETT COL", "Main Deck 1"));
        stations.add(new ChargingStation(30, 30,30,30,
                "RL", 0, "GA GWINNETT COL", "Student Housing"));
        stations.add(new ChargingStation(40, 40,40,40,
                "I", 2, "GA GWINNETT COL", "Faculty Lot"));

        ListView lvStations = (ListView) findViewById(R.id.lvStations);
        adapter = new CustomArrayAdapter(this, R.layout.charge_station, stations);
        lvStations.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stations.add(new ChargingStation(10, 10,10,10,
                        "B", 2, "GA GWINNETT COL", "A New Location!"));
                adapter.notifyDataSetChanged();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stations.remove(stations.size()-1);
                adapter.notifyDataSetChanged();
            }
        });


    }
}
