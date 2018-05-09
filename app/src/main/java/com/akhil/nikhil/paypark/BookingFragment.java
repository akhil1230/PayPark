package com.akhil.nikhil.paypark;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    private RecyclerView recyclerView ;

    private List<BookDataModel> list ;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));


        list = new ArrayList<>();

        get_bookings();

    }

    public class view_holder extends RecyclerView.ViewHolder
    {

        public TextView location , time;

        public view_holder(View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);



            time = itemView.findViewById(R.id.booking_time);

        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            final BookDataModel data = list.get(position);

            holder.location.setText(data.address);



            holder.time.setText(getDate(Long.parseLong(data.time)));

            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = String.format(Locale.ENGLISH, "geo:%s,%s", data.lat, data.lng);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getContext().startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void get_bookings()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace("." ,"");

        database.getReference().child("bookings").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    BookDataModel data = dataSnapshot1.getValue(BookDataModel.class);

                   data.time = dataSnapshot1.getKey();

                        list.add(data);
                    }

                Collections.reverse(list);


                recyclerView.setAdapter(new Adapter());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




    public static String getDate(long milliSeconds )
    {
        String dateFormat = "dd/MMM/yyyy hh:mm" ;

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
