package com.example.googlemaps;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class listAdapter implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter
{
    Context context;
    GoogleMap mMap;

    public listAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setInfoWindowAdapter(listAdapter.this);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker)
    {
        Toast toast = Toast.makeText(context, "ver carta", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker)
    {
        View infoView = LayoutInflater.from(context).inflate(R.layout.ly_ubicacion, null);
        TextView nombre = (TextView) infoView.findViewById(R.id.txtNombre);
        TextView decano = (TextView) infoView.findViewById(R.id.txtDecano);
        TextView contacto = (TextView) infoView.findViewById(R.id.txtContacto);
        TextView direccion = (TextView) infoView.findViewById(R.id.txtDireccion);
        TextView latitud = (TextView) infoView.findViewById(R.id.txtLatitud);
        TextView longitud = (TextView) infoView.findViewById(R.id.txtLongitud);
        ImageView logo = (ImageView) infoView.findViewById(R.id.imageLogo);
        Datos datos = (Datos) marker.getTag();
        nombre.setText(datos.getNombre());
        decano.setText(datos.getDecano());
        contacto.setText(datos.getContacto());
        direccion.setText(datos.getDireccion());
        latitud.setText(datos.getLatitud().toString());
        longitud.setText(datos.getLongitud().toString());
        Glide.with(context).load(datos.getLogo()).into(logo);
        return infoView;
    }

    public void coordenadas(ArrayList<Datos> point) {
        for (int i = 0; i < point.size(); i++)
        {
            Datos datos = point.get(i);
            Marker map = mMap.addMarker(new
                    MarkerOptions().position(new LatLng(datos.getLatitud(), datos.getLongitud()))
                    .title(datos.getNombre())
                    .snippet(datos.getDireccion()));
            map.setTag(datos);
        }
    }

}