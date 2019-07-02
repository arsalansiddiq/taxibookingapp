package com.taxibooking.user.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.taxibooking.user.R;
import com.taxibooking.user.Utils.MyBoldTextView;
import com.taxibooking.user.Utils.MyButton;
import com.taxibooking.user.Utils.MyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarBookingActivity extends AppCompatActivity {
    CarBookingAdapter bookingAdapter;
    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.lblTitle)
    MyBoldTextView lblTitle;
    @BindView(R.id.lnrTitle)
    LinearLayout lnrTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        ButterKnife.bind(this);
    }

    void bindBookingData(ArrayList<String> result) {
        bookingAdapter = new CarBookingAdapter(result);
        //  recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (bookingAdapter != null && bookingAdapter.getItemCount() > 0) {
            errorLayout.setVisibility(View.GONE);
            recyclerView.setAdapter(bookingAdapter);
        } else {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.backArrow)
    public void onViewClicked() {
        onBackPressed();
    }

    private class CarBookingAdapter extends RecyclerView.Adapter<CarBookingAdapter.MyViewHolder> {
        ArrayList<String> bookingCollection;

        public CarBookingAdapter(ArrayList<String> array) {
            this.bookingCollection = array;
        }

        public void append(String item) {
            bookingCollection.add(item);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_car_bookings, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return bookingCollection.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txtVendorName)
            MyTextView txtVendorName;
            @BindView(R.id.txtCarModel)
            MyTextView txtCarModel;
            @BindView(R.id.txtAvailbleSeats)
            MyTextView txtAvailbleSeats;
            @BindView(R.id.txtTotalPrice)
            MyTextView txtTotalPrice;
            @BindView(R.id.saveBTN)
            MyButton btnBooking;
            @BindView(R.id.cv_mainframe)
            CardView cvMainframe;

            MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
