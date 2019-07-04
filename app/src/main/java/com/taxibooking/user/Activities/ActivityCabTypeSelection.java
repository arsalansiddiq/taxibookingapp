package com.taxibooking.user.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.taxibooking.user.Helper.SharedHelper;
import com.taxibooking.user.R;

public class ActivityCabTypeSelection extends AppCompatActivity {
    public Context context = ActivityCabTypeSelection.this;

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btnWithinCity:
                    intent = new Intent(ActivityCabTypeSelection.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnOutsideCityCar:
                    intent = new Intent(ActivityCabTypeSelection.this, CarTripSearchingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnOutsideCityBus:
                    intent = new Intent(ActivityCabTypeSelection.this, BusTripSearchingActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedHelper.getKey(context,"login_by").equals("facebook"))
            FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_cab_selection);

        findViewById(R.id.btnWithinCity).setOnClickListener(buttonOnClickListener);
        findViewById(R.id.btnOutsideCityCar).setOnClickListener(buttonOnClickListener);
        findViewById(R.id.btnOutsideCityBus).setOnClickListener(buttonOnClickListener);

    }
}
