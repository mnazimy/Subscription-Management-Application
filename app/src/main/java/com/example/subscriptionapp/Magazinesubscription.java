package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.Models.Subscriptionmodel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Magazinesubscription extends AppCompatActivity {

    TextView textView, totalprice, numberofdays;
    TextInputLayout selectdate;
    Button addsubscription;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazinesubscription);

        textView = findViewById(R.id.textView);
        selectdate = findViewById(R.id.selectdate);
        totalprice = findViewById(R.id.totalprice);
        numberofdays = findViewById(R.id.numberofdays);
        addsubscription = findViewById(R.id.addsubscription);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Gson gson = new Gson();
        Magazinemodel data = gson.fromJson(getIntent().getStringExtra("Magazine"), Magazinemodel.class);

        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(
                        Pair.create(
                                MaterialDatePicker.todayInUtcMilliseconds(),
                                MaterialDatePicker.todayInUtcMilliseconds()
                        )
                )
                .build();

        textView.setText("Magazine Name : " + data.getName() +"\n\n"+ data.getLanguage() +", "+ data.getType()
                +", "+ data.getStatus() +"\n\nPrice : "+ data.getPrice() + ", " + data.getFrequency() + "times a month");

        float times = Float.parseFloat(data.getFrequency());
        float Price = Float.parseFloat(data.getPrice());
        float price = times * Price;
        totalprice.setText(""+price);

        numberofdays.setText("30 days");

        selectdate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Pair<Long, Long> pair= (Pair<Long, Long>) selection;
                        java.util.Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        java.util.Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar1.setTimeInMillis(pair.first);
                        calendar2.setTimeInMillis(pair.second);
                        Date date1 = calendar1.getTime();
                        Date date2 = calendar2.getTime();
                        SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
                        long time1 = date1.getTime();
                        long time2 = date2.getTime();
                        long timeDiff;
                        if(time1 > time2) {
                            timeDiff = time1 - time2;
                        } else {
                            timeDiff = time2 - time1;
                        }
                        int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24)) + 1;
                        daysDiff = daysDiff / 30;
                        String formattedDate1  = format.format(calendar1.getTime());
                        String formattedDate2  = format.format(calendar2.getTime());
                        formattedDate1  = formattedDate1 + " to " + formattedDate2;
                        selectdate.getEditText().setText(formattedDate1);
                        numberofdays.setText(""+daysDiff + " months" );
                        float price = (float)daysDiff * Float.parseFloat(data.getPrice()) * Float.parseFloat(data.getFrequency());
                        totalprice.setText(""+price);

//                        float price = (float)daysDiff * Float.parseFloat(data.getPrice());
//                        totalprice.setText(""+price);
//                        calendar1.setTimeInMillis(pair.first);
//                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                        String formattedDate1  = format.format(calendar1.getTime());
//                        formattedDate1
//                        calendar2.setTimeInMillis(pair.second);
//                        String formattedDate2  = format.format(calendar2.getTime());
//                        formattedDate  = formattedDate + " to " +format.format(calendar.getTime());
//                        selectdate.setText(formattedDate);
                    }
                });
            }
        });

        addsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subscriptionmodel subscriptionmodel = new Subscriptionmodel(
                        data.getName(), "Magazine",
                        totalprice.getText().toString(),
                        selectdate.getEditText().getText().toString()+ " "+numberofdays.getText(),
                        "Not Paid"
                );

                database.getReference().child("Subscriptions")
                        .child(auth.getCurrentUser().getUid())
                        .child(data.getName())
                        .setValue(subscriptionmodel);

                Toast.makeText(Magazinesubscription.this,
                        "Subscription Added Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}