package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProceedFilterActivity extends AppCompatActivity {




    List<createaccount> parking_list ;

    RecyclerView recyclerView ;

    LinearLayout search_view ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_filter);

        parking_list = new ArrayList<>();


        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));




        get_parkings();

    }


    public class view_holder extends RecyclerView.ViewHolder
    {

        public TextView location , car_charges , bike_charges , car_heading , bike_heading ;

        public view_holder(View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);

            car_charges = itemView.findViewById(R.id.car_charge);

            bike_charges = itemView.findViewById(R.id.bike_charge);

            car_heading = itemView.findViewById(R.id.car_heading);

            bike_heading = itemView.findViewById(R.id.bike_heading);

        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_detail_list_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            final createaccount data = parking_list.get(position);

            holder.location.setText(data.address);

            holder.bike_charges.setText(data.bike_charges);

            holder.car_charges.setText(data.car_charges);

            if(getIntent().getStringExtra("type").equals("Car"))
            {
                holder.bike_charges.setVisibility(View.GONE);
                holder.bike_heading.setVisibility(View.GONE);
            }
            else {

                holder.car_charges.setVisibility(View.GONE);
                holder.car_heading.setVisibility(View.GONE);
            }

            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = String.format(Locale.ENGLISH, "geo:%s,%s", data.lat, data.lng);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            });



        }

        @Override
        public int getItemCount() {
            return parking_list.size();
        }
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



                    if(data.available.equals("yes"))
                    {

                        if(getIntent().getStringExtra("type").equals("Car"))
                        {
                            if(data.car_charges.equals(getIntent().getStringExtra("rate")))
                            {
                                parking_list.add(data);

                            }
                        }

                        if(getIntent().getStringExtra("type").equals("Bike"))
                        {
                            if(data.bike_charges.equals(getIntent().getStringExtra("rate")))
                            {
                                parking_list.add(data);

                            }
                        }

                    }
                }

                recyclerView.setAdapter(new Adapter());

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


        }

    }
}
