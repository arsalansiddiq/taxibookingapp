package com.taxibooking.user.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.taxibooking.user.Helper.CustomDialog;
import com.taxibooking.user.Helper.SharedHelper;
import com.taxibooking.user.Models.BusTripModel;
import com.taxibooking.user.R;
import com.taxibooking.user.Retrofit.ApiInterface;
import com.taxibooking.user.Retrofit.RetrofitClient;
import com.taxibooking.user.Utils.MyBoldTextView;
import com.taxibooking.user.Utils.MyButton;
import com.taxibooking.user.Utils.MyTextView;
import com.taxibooking.user.Utils.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    int mNumberOfSeats = 0;

    int fromCityId = 0;
    int toCityId = 0;
    String selectedDate = "";
    String selectedTime = "";

    String accessToken = "";
    Utilities utils = new Utilities();
    private ApiInterface mApiInterface;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_booking);
        ButterKnife.bind(this);

        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        mApiInterface = RetrofitClient.getCustomClient().create(ApiInterface.class);
        accessToken = SharedHelper.getKey(this, "token_type") + " "
                + SharedHelper.getKey(this, "access_token");


        fromCityId = getIntent().getIntExtra("fromCityId", 0);
        toCityId = getIntent().getIntExtra("toCityId", 0);
        selectedDate = getIntent().getStringExtra("dateSelected");
        selectedTime = getIntent().getStringExtra("timeSelected");

        getFilteredTrips();
    }


    private void bookBusTrip(BusTripModel tripModel, int bookingSeats, int totalPrice) {
        customDialog.show();

        String userName = SharedHelper.getKey(this, "first_name") + " " + SharedHelper.getKey(this, "last_name");
        String mobileNumber = SharedHelper.getKey(this, "mobile");
        Call<Void> call = mApiInterface.postBusBooking(accessToken, String.valueOf(tripModel.getId()), userName, mobileNumber,
                String.valueOf(fromCityId), String.valueOf(toCityId), selectedDate, selectedTime, String.valueOf(bookingSeats), String.valueOf(totalPrice));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                customDialog.dismiss();
                displayMessage("Booking Added successfully. We will contact you soon... ");
                getToHomeActivity();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("onFailure", "onFailure" + call.request().url());
                displayMessage("Something went wrong");
                customDialog.dismiss();
            }
        });

    }

    private void getToHomeActivity() {
        Intent intent = new Intent(this, ActivityCabTypeSelection.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getFilteredTrips() {

        Call<ArrayList<BusTripModel>> call = mApiInterface.getBusTrips(accessToken, String.valueOf(fromCityId), String.valueOf(toCityId), selectedDate, selectedTime);
        customDialog.show();
        call.enqueue(new Callback<ArrayList<BusTripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BusTripModel>> call, Response<ArrayList<BusTripModel>> response) {
                bindBookingData(response.body());
                customDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<BusTripModel>> call, Throwable t) {
                Log.e("onFailure", "onFailure" + call.request().url());
                customDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.backArrow)
    public void onViewClicked() {
        onBackPressed();
    }

    void bindBookingData(ArrayList<BusTripModel> result) {
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

    private void showBookingDialog(final BusTripModel tripModel) {
        mNumberOfSeats = 0;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_bus_booking);

//        AlertDialog.Builder builder = new AlertDialog.Builder(BusBookingActivity.this);
//        View view = LayoutInflater.from(BusBookingActivity.this).inflate(R.layout.dialog_bus_booking, null);
        ImageView btnAdd = dialog.findViewById(R.id.btnAdd);
        ImageView btnRemove = dialog.findViewById(R.id.btnRemove);
        final MyTextView txtNumberOfSeats = dialog.findViewById(R.id.txtNumberSeats);
        final MyTextView txtTotalPrice = dialog.findViewById(R.id.txtTotalPrice);
        Button btnBook = dialog.findViewById(R.id.btnBook);
        mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());
                if (mNumberOfSeats < Integer.parseInt(tripModel.getAvailable_seat())) {
                    mNumberOfSeats = mNumberOfSeats + 1;
                } else {
                    Toast.makeText(BusBookingActivity.this, "cannot exceed total available seats", Toast.LENGTH_SHORT).show();
                }
                txtNumberOfSeats.setText(mNumberOfSeats + "");
                txtTotalPrice.setText("Total Rent = " + (mNumberOfSeats * Integer.parseInt(tripModel.getRate())));

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());
                if (mNumberOfSeats < 1) {
                    mNumberOfSeats = 0;
                } else {
                    mNumberOfSeats = mNumberOfSeats - 1;
                }
                txtNumberOfSeats.setText(mNumberOfSeats + "");

                txtTotalPrice.setText("Total Rent = " + (mNumberOfSeats * Integer.parseInt(tripModel.getRate())));

            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());
                if (mNumberOfSeats == 0) {
                    Toast.makeText(BusBookingActivity.this, "Please select a valid number of seats", Toast.LENGTH_SHORT).show();
                    return;
                }

                bookBusTrip(tripModel, mNumberOfSeats, mNumberOfSeats * Integer.parseInt(tripModel.getRate()));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        try {
            Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } catch (Exception e) {
            try {
                Toast.makeText(this, "" + toastString, Toast.LENGTH_SHORT).show();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public class BusBookingAdapter extends RecyclerView.Adapter<BusBookingAdapter.MyViewHolder> {
        ArrayList<BusTripModel> bookingCollection;

        public BusBookingAdapter(ArrayList<BusTripModel> array) {
            this.bookingCollection = array;
        }

        public void append(BusTripModel item) {
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
            final BusTripModel dataObject = bookingCollection.get(position);
            holder.txtVendorName.setText(dataObject.getVendor() + "");
            holder.txtAvailbleSeats.setText(dataObject.getAvailable_seat() + "");
            holder.txtTotalPrice.setText((Integer.parseInt(dataObject.getRate())) + "");
            holder.txtTripDetail.setText(dataObject.getFrom_city().getName() + " - " + dataObject.getTo_city().getName());
            holder.btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBookingDialog(dataObject);
                }
            });
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


            }
        }
    }

}
