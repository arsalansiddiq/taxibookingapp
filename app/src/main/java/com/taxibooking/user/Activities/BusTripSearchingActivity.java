package com.taxibooking.user.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.taxibooking.user.R;
import com.taxibooking.user.Utils.MyBoldTextView;
import com.taxibooking.user.Utils.MyButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusTripSearchingActivity extends AppCompatActivity {

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
    @BindView(R.id.spnTripTime)
    Spinner spnTripTime;
    @BindView(R.id.btnSearchTrip)
    MyButton btnSearchTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_trip_searching);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backArrow, R.id.btnSearchTrip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                onBackPressed();
                break;
            case R.id.btnSearchTrip:
                Intent intent = new Intent(this,BusBookingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
