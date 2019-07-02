package com.taxibooking.user.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class BusBookingActivity extends AppCompatActivity {
    BusBookingAdapter bookingAdapter;
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
        setContentView(R.layout.activity_bus_booking);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.backArrow)
    public void onViewClicked() {
        onBackPressed();
    }
    void bindBookingData(ArrayList<String> result){
        bookingAdapter = new BusBookingAdapter(result);
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

    private class BusBookingAdapter extends RecyclerView.Adapter<BusBookingAdapter.MyViewHolder> {
        ArrayList<String> bookingCollection;

        public BusBookingAdapter(ArrayList<String> array) {
            this.bookingCollection = array;
        }

        public void append(String item) {
            bookingCollection.add(item);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_bus, parent, false);
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

            MyTextView txtVendorName, txtTripDetail, txtAvailbleSeats, txtTotalPrice;
            Button btnBooking;

            public MyViewHolder(View itemView) {
                super(itemView);
                txtVendorName = (MyTextView) itemView.findViewById(R.id.txtVendorName);
                txtTripDetail = (MyTextView) itemView.findViewById(R.id.txtTripDetail);
                txtAvailbleSeats = (MyTextView) itemView.findViewById(R.id.txtAvailbleSeats);
                txtTotalPrice = (MyTextView) itemView.findViewById(R.id.txtTotalPrice);
                btnBooking = (MyButton) itemView.findViewById(R.id.btnBook);

                btnBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

            }
        }
    }

}
