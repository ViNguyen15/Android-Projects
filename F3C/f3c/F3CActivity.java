package edu.ggc.lutz.ggcf3c;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import edu.ggc.lutz.ggcf3c.chargepoint.ChargingStation;
import edu.ggc.lutz.ggcf3c.chargepoint.json.ChargePoint;
import edu.ggc.lutz.ggcf3c.chargepoint.json.StationList;
import edu.ggc.lutz.ggcf3c.chargepoint.json.Summary;
import java.util.Calendar;


public class F3CActivity extends AppCompatActivity {

    private ArrayList<ChargingStation> results;
    private CustomArrayAdapter adapter;
    private TextToSpeech tts;
    private ChargingStation[] stations;

    public static final Gson gson;
    public static final String TAG = "f3c";

    static {
        gson = new Gson();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f3_c);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.clear();
                adapter.notifyDataSetChanged();
                new FetchStatusAsyncTask().execute(stations);
            }
        });

        stations = new ChargingStation[4];
        stations[0] = new ChargingStation(33.9820, -84.0031, 33.9811, -84.0048, "B Building");
        stations[1] = new ChargingStation(33.9805, -84.0063, 33.9795, -84.0080, "Res Life 1000");
        stations[2] = new ChargingStation(33.9819, -83.9991, 33.9809, -84.0008, "Parking Deck");
        stations[3] = new ChargingStation(33.9779, -84.0018, 33.9770, -84.0035, "I Building");

        results = new ArrayList<>();
        ListView lvResults = (ListView) findViewById(R.id.lvResults);
        adapter = new CustomArrayAdapter(this, R.layout.charge_station, results);
        lvResults.setAdapter(adapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChargePoint chargepoint = gson.fromJson(results.get(i).getJson(),
                        ChargePoint.class);
                StationList slist = chargepoint.getStationList();
                List<Summary> summaries = slist.getSummaries();
                Summary summary = summaries.get(0);
                long available = summary.getMapData().getLevel2().getPaid()
                        .getAvailable();
                String toSpeak = available + "! " +
                        ((available == 1) ? "charger is" : "chargers are") +
                        " available." + "This location is " + summary.getDescription();
                Toast.makeText(getApplicationContext(), toSpeak.replace("!", ""),
                        Toast.LENGTH_LONG).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Calendar now = Calendar.getInstance();
            String month = new SimpleDateFormat("MMMM").format(now.getTime());
            String result = "F3C@GCC - Find EV Charging at GGC\n" +
                    "By Bob Lutz for ITEC 4550, " + month + " " + now.get(Calendar.YEAR);
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content),
                    result, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(tts !=null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
    }

    class FetchStatusAsyncTask extends AsyncTask<ChargingStation, Void,
            ChargingStation[]> {
        @Override
        protected ChargingStation[] doInBackground(ChargingStation... stations) {
            for (ChargingStation cs : stations) {
                StringBuilder result = new StringBuilder();
                HttpURLConnection urlConnection = null;
                String line;
                try {
                    urlConnection = (HttpURLConnection) cs.constructRESTfulURL().openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while ((line = reader.readLine()) != null) result.append(line);

                    cs.setJson(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
            return stations;
        }

        @Override
        protected void onPostExecute(ChargingStation... stations) {
            super.onPostExecute(stations);
            for (ChargingStation cs : stations) {
                // temporarily clobber 2 values to demonstrate that sort works ok
                boolean sim = false;
                if (sim) {
                    if (cs.getNickName().equals("Res Life 1000"))
                        cs.setJson(cs.getJson().replace("{\"available\":2,", "{\"available\":0,"));
                    if (cs.getNickName().equals("I Building"))
                        cs.setJson(cs.getJson().replace("{\"available\":2,", "{\"available\":1,"));
                }
                results.add(cs);
            }
            Collections.sort(results, Collections.reverseOrder());
            adapter.notifyDataSetChanged();
        }
    }
}
