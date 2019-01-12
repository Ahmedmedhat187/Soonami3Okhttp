package com.example.ahmed.soonami3;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    ListView listView;
    TextView empty;
    String data;

    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);
        empty = (TextView)findViewById(R.id.empty);
        listView.setEmptyView(empty);

        getWebservice();
    }




    private void getWebservice() {
        client = new OkHttpClient();
        final Request request = new Request.Builder().url(USGS_REQUEST_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        empty.setText("Failure !");
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            data = response.body().string();
                            ArrayList<Event>  allEvents = extractFeatureFromJson(data);
                            updateUi(allEvents);

                        } catch (IOException ioe) {
                            empty.setText("Error during get body");
                        }
                    }
                });
            }
        });
    }




    private ArrayList<Event>  extractFeatureFromJson(String earthquakeJSON) {
        Event currentEvent ;
        ArrayList<Event> allEvents = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");

            // If there are results in the features array
            if (featureArray.length() > 0) {

                for(int i=0 ; i<featureArray.length() ; i++){
                    JSONObject currentFeature = featureArray.getJSONObject(i);
                    JSONObject properties = currentFeature.getJSONObject("properties");
                    currentEvent = new Event(properties.getString("title"),
                            properties.getLong("time"),properties.getInt("tsunami"));
                    allEvents.add(currentEvent);
                }
                return allEvents;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }


    /**
     * Update the screen to display information from the given {@link Event}.
     */
    private void updateUi(ArrayList<Event> allEvents) {
        EventAdapter adapter = new EventAdapter( this , allEvents);
        listView.setAdapter(adapter);
    }




}
