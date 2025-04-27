package com.example.androidjavacourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YtjSearch extends AppCompatActivity {

    public static final String TAG ="Ytj Search";
    String url;
    RequestQueue requestQueue;
    Cache cache;
    Network network;
    ProgressBar progressBar;
    Item item;
    ArrayList<Item> items;
    YtjAdapter mAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ytj_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d(TAG, "empty search");
            return;
        }
        // get data via the key
        String inputText = extras.getString("inputText");
        if (inputText != null) {
            url = "https://avoindata.prh.fi/opendata-ytj-api/v3/companies?name=" + inputText + "&companyForm=OY";
            Log.d(TAG, url);

        }

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.ytjToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        items = new ArrayList<Item>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        requestQueSetup();
        jsonObjRequest();
    }
    public void requestQueSetup(){
        //      Instantiate the cache
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        //      Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        //      Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        //      Start the queue
        requestQueue.start();
    }
    public void jsonObjRequest(){
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Got response");
                Log.d(TAG, response.toString());
                progressBar.setVisibility(View.INVISIBLE);

                try{
                JSONArray responseItems = (JSONArray) response.getJSONArray("companies");
                    for (int i=0; i < responseItems.length(); i++) {
                        try{
                        JSONObject obj = responseItems.getJSONObject(i);
                        item = new Item();
                        item.businessId = obj.getJSONObject("businessId").getString("value");
                        item.name = obj.getJSONArray("names").getJSONObject(0).getString("name");
                        item.companyForm = "OY";
                        item.registrationDate = obj.getJSONObject("businessId").getString("registrationDate");
                        items.add(item);} catch (Exception e) {
                            Log.e(TAG, "Error " + e.getMessage());
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error " + e.getMessage());
                }
                mAdapter = new YtjAdapter(items);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Request failed");
                Log.e(TAG, error.toString());
            }
        });
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        Log.d(TAG, "RetryPolicy käsitelty");
        requestQueue.add(jsonRequest);
    }
}