package com.fmm.uber_app;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    private Marker driverMarker=null;
    private MarkerOptions driverMarkerOptions;

    private DatabaseReference driverPositionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_map));

        LatLng manaus = new LatLng(-3.141590,-60.020320);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(manaus));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng newPosition = latLng;
                Toast.makeText(getApplicationContext(),"Adicionou novo marcador: "+newPosition.latitude,Toast.LENGTH_SHORT).show();
                if(driverMarker!=null)driverMarker.remove();

                driverMarkerOptions = new MarkerOptions();
                driverMarkerOptions.title("Sua Posição Atual").position(newPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.car));

                driverMarker = mMap.addMarker(driverMarkerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition));

                driverPositionReference = FirebaseDatabase.getInstance().getReference().child("position");



                driverPositionReference.child(FirebaseAuth.getInstance().getUid()).child("lat").setValue(newPosition.latitude);
                driverPositionReference.child(FirebaseAuth.getInstance().getUid()).child("long").setValue(newPosition.longitude);



            }
        });

    }

    @Override
    public void onMapClick(LatLng latLng) {




    }
}