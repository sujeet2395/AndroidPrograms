package com.androidmania.apps.apptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // json object response url
    private static final String TAG="weather";
    private static final String APIKEY="b2c40d97678261ea137ce9227d716f0e";
    private String Location="New Delhi";
    private String Country="IN";
    private String url = "https://api.openweathermap.org/data/2.5/weather?q="+Location+","+Country+"&appid="+APIKEY;

    private Button RequestBtn;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Gson gson=new Gson();
        String jsonstr="{\"name\":\"MyCar\"}";
        Car car=gson.fromJson(jsonstr, Car.class);
        Car car=new Car();
        String ss=car.getName();
        System.out.println(car);
        Toast.makeText(this, car.toString(), Toast.LENGTH_SHORT).show();
        */
        RequestBtn = (Button) findViewById(R.id.button);
        txtResponse = (TextView) findViewById(R.id.textView);
        RequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    StringBuffer jsonString=new StringBuffer();
                    JSONObject jsonObject= response.getJSONObject("main");
                    jsonString.append(String.valueOf("temp : "+(Double.parseDouble(jsonObject.getString("temp"))-Double.valueOf(273.15)))).append("\n")
                    .append("pressure :"+jsonObject.getInt("pressure")).append("\n")
                            .append("humidity : "+jsonObject.getInt("humidity")).append("\n")
                            .append("temp min : "+String.valueOf(Double.parseDouble(jsonObject.getString("temp_min"))-Double.valueOf(273.15))).append("\n")
                            .append("temp max : "+String.valueOf(Double.parseDouble(jsonObject.getString("temp_max"))-Double.valueOf(273.15)))
                            .append("\nwind : "+response.getString("wind"))
                            .append("\nlocation : "+response.getString("name"))
                            .append("\nvisibility : "+response.getString("visibility"));

                    txtResponse.setText(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue=Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }
}
