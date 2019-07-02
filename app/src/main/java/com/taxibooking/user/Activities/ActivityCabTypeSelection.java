package com.taxibooking.user.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.taxibooking.user.R;

import javax.annotation.Nullable;

public class ActivityCabTypeSelection extends AppCompatActivity {
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
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_cab_selection);

        findViewById(R.id.btnWithinCity).setOnClickListener(buttonOnClickListener);
        findViewById(R.id.btnOutsideCityCar).setOnClickListener(buttonOnClickListener);
        findViewById(R.id.btnOutsideCityBus).setOnClickListener(buttonOnClickListener);


    }
}
