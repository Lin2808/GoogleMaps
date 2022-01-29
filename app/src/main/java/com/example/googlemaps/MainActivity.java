package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private String URLstring = "https://my-json-server.typicode.com/Lin2808/Json/db";
    ArrayList<Datos> datosArrayList;
    listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(adapter);

        ExtraerDatos();
    }
    private void ExtraerDatos()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            datosArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("facultades");

                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                Datos datos = new Datos();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                datos.setNombre(dataobj.getString("nombre"));
                                datos.setDireccion(dataobj.getString("direccion"));
                                datos.setDecano(dataobj.getString("decano"));
                                datos.setContacto(dataobj.getString("contacto"));
                                datos.setLogo(dataobj.getString("logo"));
                                datos.setLatitud(dataobj.getDouble("latitud"));
                                datos.setLongitud(dataobj.getDouble("longitud"));
                                datosArrayList.add(datos);
                            }
                            adapter.coordenadas(datosArrayList);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}