package com.taxibooking.user.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.taxibooking.user.Helper.CustomDialog;
import com.taxibooking.user.Helper.SharedHelper;
import com.taxibooking.user.Models.CitiesModel;
import com.taxibooking.user.R;
import com.taxibooking.user.Retrofit.ApiInterface;
import com.taxibooking.user.Retrofit.RetrofitClient;
import com.taxibooking.user.Utils.MyBoldTextView;
import com.taxibooking.user.Utils.MyButton;
import com.taxibooking.user.Utils.MyTextView;
import com.taxibooking.user.Utils.Utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    MyTextView btnSelectDate;
    @BindView(R.id.btnSearchTrip)
    MyButton btnSearchTrip;
    DatePickerDialog datePickerDialog;
    String scheduledDate = "";
    Utilities utils = new Utilities();
    private boolean afterToday;
    String accessToken = "";
    private ApiInterface mApiInterface;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_trip_searching);
        ButterKnife.bind(this);

        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        mApiInterface = RetrofitClient.getCustomClient().create(ApiInterface.class);
        accessToken = SharedHelper.getKey(this, "token_type") + " "
                + SharedHelper.getKey(this, "access_token");
        getAllCities();
    }

    private void getAllCities() {
        Call<ArrayList<CitiesModel>> call = mApiInterface.getAllCities(accessToken);
        customDialog.show();
        call.enqueue(new Callback<ArrayList<CitiesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CitiesModel>> call, Response<ArrayList<CitiesModel>> response) {
                bindCitiesSpinner(response.body());
                customDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<CitiesModel>> call, Throwable t) {
                Log.e("onFailure", "onFailure" + call.request().url());
                customDialog.dismiss();
            }
        });

    }


    private void bindCitiesSpinner(final ArrayList<CitiesModel> result) {
        ArrayAdapter<CitiesModel> interestAdapter = new ArrayAdapter<CitiesModel>(this, R.layout.spinner_item, result) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                // Then you can get the current item using the values array (Users array) and the current position
                // You can NOW reference each method you has created in your bean object (User class)
                label.setText(result.get(position).getName());

                // And finally return your dynamic (or custom) view for each spinner item
                return label;
            }

            //    ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_dropdown_item, collection) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(result.get(position).getName());
                return view;
            }
        };

        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFromCity.setAdapter(interestAdapter);
        spnToCity.setAdapter(interestAdapter);

        spnToCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnFromCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                        scheduledDate = year + "-" + choosedMonth + "-" + choosedDate;
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
                intent.putExtra("fromCityId", (((CitiesModel) spnFromCity.getSelectedItem()).getId()));
                intent.putExtra("toCityId", (((CitiesModel) spnToCity.getSelectedItem()).getId()));
                intent.putExtra("dateSelected", scheduledDate);
                startActivity(intent);
                break;
        }
    }
}
