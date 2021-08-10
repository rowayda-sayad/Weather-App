package com.example.weatherappudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private String API_KEY = "925d0ed7bf7bd8a985d4eee1d9d9eaa4";
    private String Uri = "https://api.openweathermap.org/data/2.5/weather?q=Toronto&appid=925d0ed7bf7bd8a985d4eee1d9d9eaa4";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String desc = jsonObjectWeather.getString("description");

                    JSONObject jsonObjectTemp = jsonObject.getJSONObject("main");
                    double temp = jsonObjectTemp.getDouble("temp") - 273.15;

                    Toast.makeText(MainActivity.this, "Toronto: " + df.format(temp) + "C, " + desc, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong! " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


}