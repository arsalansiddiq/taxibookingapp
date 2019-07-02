package com.taxibooking.user.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.taxibooking.user.R;
import com.taxibooking.user.Utils.MyBoldTextView;
import com.taxibooking.user.Utils.MyButton;
import com.taxibooking.user.Utils.MyEditText;
import com.taxibooking.user.Utils.Utilities;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarTripSearchingActivity extends AppCompatActivity {

    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.lblTitle)
    MyBoldTextView lblTitle;
    @BindView(R.id.lnrTitle)
    LinearLayout lnrTitle;
    @BindView(R.id.spnFromCity)
    Spinner spnFromCity;
    @BindView(R.id.spnToCity)
    Spinner spnToCity;
    @BindView(R.id.btnSelectDate)
    MyEditText btnSelectDate;
    @BindView(R.id.btnSearchTrip)
    MyButton btnSearchTrip;
    DatePickerDialog datePickerDialog;
    String scheduledDate = "";
    Utilities utils = new Utilities();
    private boolean afterToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_trip_searching);
        ButterKnife.bind(this);
    }

    private void openDatePicker() {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // set day of month , month and year value in the edit text
                        String choosedMonth = "";
                        String choosedDate = "";
                        String choosedDateFormat = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        scheduledDate = choosedDateFormat;
                        try {
                            choosedMonth = Utilities.getMonth(choosedDateFormat);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (dayOfMonth < 10) {
                            choosedDate = "0" + dayOfMonth;
                        } else {
                            choosedDate = "" + dayOfMonth;
                        }
                        afterToday = Utilities.isAfterToday(year, monthOfYear, dayOfMonth);
                        btnSelectDate.setText(choosedDate + " " + choosedMonth + " " + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000) + (1000 * 60 * 60 * 24 * 7));
        datePickerDialog.show();
    }

    @OnClick({R.id.backArrow, R.id.btnSelectDate, R.id.btnSearchTrip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                onBackPressed();
                break;
            case R.id.btnSelectDate:
                openDatePicker();
                break;
            case R.id.btnSearchTrip:
                Intent intent = new Intent(this, CarBookingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
