package com.akhil.nikhil.paypark;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class map_frag extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    ProgressDialog progress_bar;

    private FusedLocationProviderClient mFusedLocationClient;

    List<createaccount> parking_list ;


    LinearLayout search_view ;

    public map_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_frag, container, false);
        progress_bar = new ProgressDialog(getActivity());
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();

        parking_list = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        search_view = view.findViewById(R.id.search_view);

        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(getActivity() , PlacePickerActivity.class) , 100);
            }
        });

        MapView mapFragment = view.findViewById(R.id.map);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();
        mapFragment.getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.

        progress_bar.hide();


        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rarwe situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

                        }
                    }
                });

        get_parkings();
    }



    private void get_parkings()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        database.getReference().child("parking_details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    createaccount data = dataSnapshot1.getValue(createaccount.class);

                    if(data.available.equals("yes")) {

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(data.lat), Double.parseDouble(data.lng)))
                                .title("Car Rs " + data.car_charges + " , " + " Bike Rs " + data.bike_charges)
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100)
        {

            LatLng sydney = new LatLng(Double.parseDouble(data.getStringExtra("lat")), Double.parseDouble(data.getStringExtra("lng")));
            mMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

        }

    }
}
