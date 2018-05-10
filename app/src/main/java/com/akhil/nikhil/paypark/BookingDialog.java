package com.akhil.nikhil.paypark;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by charanghumman on 09/05/18.
 */

public class BookingDialog extends Dialog

{

    TextView bike_charges , car_charges , address ;

    Button book ;

    RadioButton pay_by_card , pay_by_cash ;

    EditText card_number , card_expiry ;

    public BookingDialog(@NonNull final Context context, int themeResId , final createaccount data) {
        super(context, themeResId);

        setContentView(R.layout.book_dialog_layout);

        address = findViewById(R.id.address);

        address.setText(data.address);

        bike_charges = findViewById(R.id.bike_charges);

        car_charges = findViewById(R.id.car_charges);

        bike_charges.setText(data.bike_charges);

        car_charges.setText(data.car_charges);

        pay_by_card = findViewById(R.id.card_);

        pay_by_cash = findViewById(R.id.pay_);

        book = findViewById(R.id.book);

        card_number = findViewById(R.id.card_number);

        card_expiry = findViewById(R.id.card_expiry);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String payment_mode = "";


                if(pay_by_card.isChecked())
                {
                    payment_mode = "Card";

                    if(card_number.getText().toString().length() < 15)
                    {
                        Toast.makeText(context , "enter valid card number" , Toast.LENGTH_SHORT).show();

                        return;
                    }

                    if(card_expiry.getText().toString().length() < 4)
                    {
                        Toast.makeText(context , "enter valid date" , Toast.LENGTH_SHORT).show();

                        return;
                    }
                }

                if(pay_by_cash.isChecked())
                {
                    payment_mode = "Cash";
                }

                if(payment_mode.equals(""))
                {
                    Toast.makeText(context , "Please select payment mode" , Toast.LENGTH_SHORT).show();

                    return;
                }




                BookDataModel data_model = new BookDataModel( data.sp_email , data.address , data.car_charges , data.bike_charges , payment_mode  , data.lat , data.lng  );


                FirebaseAuth auth = FirebaseAuth.getInstance();

                String email = auth.getCurrentUser().getEmail().replace(".","");

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                database.getReference().child("bookings").child(email).child(String.valueOf(System.currentTimeMillis())).setValue(data_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if(task.isSuccessful())
                        {
                            Toast.makeText(context , "Booking Done" , Toast.LENGTH_SHORT).show();

                            dismiss();
                        }
                    }
                });




            }
        });


    }
}
