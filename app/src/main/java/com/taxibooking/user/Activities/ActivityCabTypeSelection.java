package com.taxibooking.user.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.taxibooking.user.R;

import javax.annotation.Nullable;

public class ActivityCabTypeSelection extends AppCompatActivity {
    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnWithinCity:
                    break;
                case R.id.btnOutsideCityCar:
                    break;
                case R.id.btnOutsideCityBus:
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
