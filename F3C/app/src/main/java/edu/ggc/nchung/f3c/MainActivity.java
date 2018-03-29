package edu.ggc.nchung.f3c;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import edu.ggc.nchung.f3c.chargepoint.ChargePoint;
import edu.ggc.nchung.f3c.chargepoint.StationList;
import edu.ggc.nchung.f3c.chargepoint.Summary;


public class MainActivity extends AppCompatActivity {

    // Instantiate instance variables
    private CustomListAdapter adapter;
    private ArrayList<ChargingStation> stationResults;
    private ListView listResults;
    private Gson gson;
    TextToSpeech tts;

    String[] listNames = {
            "B Building",
            "Res Life 1000",
            "Parking Deck",
            "I Building",
    };

    final static String urlB = "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9820,\"ne_lon\":-84.0031,\"sw_lat\":33.9811,\"sw_lon\":-84.0048}}";
    final static String urlRes = "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9805,\"ne_lon\":-84.0063,\"sw_lat\":33.9795,\"sw_lon\":-84.0080}}";
    final static String urlParking = "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9819,\"ne_lon\":-83.9991,\"sw_lat\":33.9809,\"sw_lon\":-84.0008}}";
    final static String urlI = "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9779,\"ne_lon\":-84.0018,\"sw_lat\":33.9770,\"sw_lon\":-84.0035}}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize
        FloatingActionButton fabRefresh = (FloatingActionButton) findViewById(R.id.fab_refresh);

        stationResults = new ArrayList<>();
        adapter = new CustomListAdapter(this, R.layout.custom_list, stationResults);
        listResults = (ListView) findViewById(R.id.LvResults);
        listResults.setAdapter(adapter);

        // Calls into CustomListAdapter.java (This still works)
        // App Break
        listResults.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                ChargePoint chargepoint = gson.fromJson(stationResults.get(position).getJson(),
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

                //String selectListNames = listNames[+position];
                //Toast.makeText(getApplicationContext(), selectListNames, Toast.LENGTH_LONG).show();
                */
            }
        });

        /** Old code that works with TextView Display **/
        //CustomListAdapter.java class extends ArrayAdapter base class
        /*
        final CustomListAdapter adapter = new CustomListAdapter(this, listNames);
        listResults = (ListView) findViewById(R.id.LvResults);
        listResults.setAdapter(adapter);
        listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectListNames = listNames[+position];
                Toast.makeText(getApplicationContext(), selectListNames, Toast.LENGTH_LONG).show();
            }
        });
        */

        // Refresh the ListView list with FAB button.
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.custom_list);
                Snackbar.make(view, "Refreshing F3C ", Snackbar.LENGTH_SHORT).show();
                //.setAction("Action", null).show();

                // new FetchASyncTask.execute(params[0], params[1]);
                new FetchASyncTask().execute(urlB, "B Building");
                new FetchASyncTask().execute(urlRes, "Res Life 1000");
                new FetchASyncTask().execute(urlParking, "Parking Deck");
                new FetchASyncTask().execute(urlI, "I Building");
                stationResults.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public class FetchASyncTask extends AsyncTask<String, Void, ArrayList<ChargingStation>> {

        public final String TAG = "aSync - doInBackground";

        @Override
        protected ArrayList<ChargingStation> doInBackground(String... params) {

            HttpURLConnection connection = null;
            StringBuilder jsonSB = new StringBuilder();
            Scanner scan = null;
            String urlBase = params[0];

            try {
                URL url = new URL(urlBase);
                connection = (HttpURLConnection) url.openConnection();
                scan = new Scanner(new BufferedInputStream(connection.getInputStream()));
                while (scan.hasNext()) {
                    jsonSB.append(scan.nextLine());
                }
                Log.i(TAG, "jsonSB URL: " + jsonSB.toString());

                // Testing: Retrieve & Traverse through JSON Object responses from URL
                JSONObject jsonObject = new JSONObject(jsonSB.toString());
                JSONObject firstObject = jsonObject.getJSONObject("station_list");

                JSONArray arrayObject = firstObject.getJSONArray("summaries");
                JSONObject objectArray0 = arrayObject.getJSONObject(0);
                JSONObject portCount = objectArray0.getJSONObject("port_count");
                int available = portCount.getInt("available");
                Log.i(TAG, "# of chargers AVAILABLE: " + available);

                String jsonTime = firstObject.getString("time");
                Log.i(TAG, "Station TIME: " + jsonTime);

                // new FetchASyncTask.execute(params[0], params[1]);
                // Declare new constructor String nickName to cs parameter argument.
                ChargingStation chargingStation = new ChargingStation(params[1]);

                // Empty Objects - Update stations object after receiving response from RESTful call.
                chargingStation.setJson(jsonSB.toString());
                stationResults.add(chargingStation);
                Log.i(TAG, "stationResults: " + stationResults);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (scan != null) {
                    scan.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return stationResults;
        }

        @Override
        protected void onPostExecute(ArrayList<ChargingStation> stationResults) {
            super.onPostExecute(stationResults);
            //for (ChargingStation cs : stationResults) {
            //   stationResults.addAll((Collection<? extends ChargingStation>) cs);
            //}
            Collections.sort(stationResults, Collections.reverseOrder());
            Log.i(TAG, "onPostExecute() - SORT" + stationResults);
            adapter.notifyDataSetChanged();
        }
    }


    /** Old code that works with TextView display **/
    /*
    public class FetchASyncTask extends AsyncTask<String, Void, String> {

        public final String TAG = "aSync - doInBackground";

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            StringBuilder jsonSB = new StringBuilder();
            Scanner scan = null;
            String urlBase = params[0];

            try {
                URL url = new URL(urlBase);
                connection = (HttpURLConnection) url.openConnection();
                scan = new Scanner(new BufferedInputStream(connection.getInputStream()));
                while (scan.hasNext()) {
                    jsonSB.append(scan.nextLine());
                }
                Log.i(TAG, "jsonSB url = " + jsonSB.toString());

                //JSON Response Parsing entire JSON String - Initalize new Json Object
                JSONObject jsonObject = new JSONObject(jsonSB.toString());

                //JSON Object: Retrieve "station_list" variable from beginning
                JSONObject parentObject = jsonObject.getJSONObject("station_list");

                //JSONObject: use parentObject to retrieve other specified arrays/objects
                JSONArray arrayObject = parentObject.getJSONArray("summaries");
                JSONObject objectSummaries = arrayObject.getJSONObject(0);
                JSONObject portCount = objectSummaries.getJSONObject("port_count");
                int availableStatus = portCount.getInt("available");
                Log.i(TAG, "# of chargers available: " + availableStatus);

                String jsonTime = parentObject.getString("time");
                Log.i(TAG, "Station TIME: " + jsonTime);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (scan != null) { scan.close(); }
                if (connection != null) { connection.disconnect(); }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            Log.i(TAG, "running onPostExecute()");
        }
    }
      */

    // Action Bar - Displays drop down menu for 'About" only -> menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings_about) {
            Toast.makeText(getApplication(),
                    "Ni Chung \n2017",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}





