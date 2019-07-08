package com.taxibooking.user.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.taxibooking.user.Models.CarTripModel;
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

@SuppressLint("SetTextI18n")
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
    int mNumberOfSeats = 0;
    int mNumberOfBackSeats = 0;

    int fromCityId = 0;
    int toCityId = 0;
    String selectedDate = "";

    String accessToken = "";
    Utilities utils = new Utilities();
    private ApiInterface mApiInterface;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        ButterKnife.bind(this);

        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        mApiInterface = RetrofitClient.getCustomClient().create(ApiInterface.class);
        accessToken = SharedHelper.getKey(this, "token_type") + " "
                + SharedHelper.getKey(this, "access_token");

        fromCityId = getIntent().getIntExtra("fromCityId", 0);
        toCityId = getIntent().getIntExtra("toCityId", 0);
        selectedDate = getIntent().getStringExtra("dateSelected");

        getFilteredTrips();

    }

    private void getFilteredTrips() {

        Call<ArrayList<CarTripModel>> call = mApiInterface.getCarTrips(accessToken, String.valueOf(fromCityId), String.valueOf(toCityId));
        customDialog.show();
        call.enqueue(new Callback<ArrayList<CarTripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CarTripModel>> call, Response<ArrayList<CarTripModel>> response) {
                bindBookingData(response.body());
                customDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<CarTripModel>> call, Throwable t) {
                Log.e("onFailure", "onFailure" + call.request().url());
                customDialog.dismiss();
            }
        });
    }

    void bindBookingData(ArrayList<CarTripModel> result) {
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

    private void bookCarTrip(CarTripModel tripModel, int bookingSeats, int bookingBackSeats, int totalPrice) {
        customDialog.show();

        String userName = SharedHelper.getKey(this, "first_name") + " " + SharedHelper.getKey(this, "last_name");
        String mobileNumber = SharedHelper.getKey(this, "mobile");
        Call<Void> call = mApiInterface.postCarBooking(accessToken, String.valueOf(tripModel.getId()), userName, mobileNumber,
                String.valueOf(fromCityId), String.valueOf(toCityId), selectedDate, String.valueOf(bookingSeats), String.valueOf(bookingBackSeats), String.valueOf(totalPrice));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                customDialog.dismiss();
                displayMessage("Booking Added successfully.  We will contact you soon... ");
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

    private void getToHomeActivity() {
        Intent intent = new Intent(this, ActivityCabTypeSelection.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showBookingDialog(final CarTripModel tripModel) {
        mNumberOfSeats = 0;
        mNumberOfBackSeats = 0;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_car_booking);

        ImageView btnAdd = dialog.findViewById(R.id.btnAdd);
        ImageView btnRemove = dialog.findViewById(R.id.btnRemove);

        ImageView btnBackAdd = dialog.findViewById(R.id.btnBackAdd);
        ImageView btnBackRemove = dialog.findViewById(R.id.btnBackRemove);

        final MyTextView txtNumberOfSeats = dialog.findViewById(R.id.txtNumberSeats);
        final MyTextView txtNumberOfBackSeats = dialog.findViewById(R.id.txtNumberBackSeats);

        final MyTextView txtTotalPrice = dialog.findViewById(R.id.txtTotalPrice);
        Button btnBook = dialog.findViewById(R.id.btnBook);
        mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());
        mNumberOfBackSeats = Integer.parseInt(txtNumberOfBackSeats.getText().toString());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().toString());
                if (mNumberOfSeats < tripModel.getFront_seat()) {
                    mNumberOfSeats = mNumberOfSeats + 1;
                } else {
                    Toast.makeText(CarBookingActivity.this, "cannot exceed total available seats", Toast.LENGTH_SHORT).show();
                }
                txtNumberOfSeats.setText(mNumberOfSeats + "");
                int totalFrontSeatRate = mNumberOfSeats * Integer.parseInt(tripModel.getFront_seat_rate());
                int totalBackSeatRate = mNumberOfBackSeats * Integer.parseInt(tripModel.getBack_seat_rate());
                txtTotalPrice.setText("Total Rent = " + (totalFrontSeatRate + totalBackSeatRate));

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

                int totalFrontSeatRate = mNumberOfSeats * Integer.parseInt(tripModel.getFront_seat_rate());
                int totalBackSeatRate = mNumberOfBackSeats * Integer.parseInt(tripModel.getBack_seat_rate());
                txtTotalPrice.setText("Total Rent = " + (totalFrontSeatRate + totalBackSeatRate));


            }
        });


        btnBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfBackSeats = Integer.parseInt(txtNumberOfBackSeats.getText().toString());
                if (mNumberOfBackSeats < tripModel.getBack_seat()) {
                    mNumberOfBackSeats = mNumberOfBackSeats + 1;
                } else {
                    Toast.makeText(CarBookingActivity.this, "cannot exceed total available seats", Toast.LENGTH_SHORT).show();
                }
                txtNumberOfBackSeats.setText(mNumberOfBackSeats + "");
                int totalFrontSeatRate = mNumberOfSeats * Integer.parseInt(tripModel.getFront_seat_rate());
                int totalBackSeatRate = mNumberOfBackSeats * Integer.parseInt(tripModel.getBack_seat_rate());
                txtTotalPrice.setText("Total Rent = " + (totalFrontSeatRate + totalBackSeatRate));
            }
        });

        btnBackRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfBackSeats = Integer.parseInt(txtNumberOfBackSeats.getText().toString());
                if (mNumberOfBackSeats < 1) {
                    mNumberOfBackSeats = 0;
                } else {
                    mNumberOfBackSeats = mNumberOfBackSeats - 1;
                }
                txtNumberOfBackSeats.setText(mNumberOfBackSeats + "");
                int totalFrontSeatRate = mNumberOfSeats * Integer.parseInt(tripModel.getFront_seat_rate());
                int totalBackSeatRate = mNumberOfBackSeats * Integer.parseInt(tripModel.getBack_seat_rate());
                txtTotalPrice.setText("Total Rent = " + (totalFrontSeatRate + totalBackSeatRate));

            }
        });


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfBackSeats = Integer.parseInt(txtNumberOfBackSeats.getText().toString());
                mNumberOfBackSeats = Integer.parseInt(txtNumberOfBackSeats.getText().toString());

                if (mNumberOfSeats > 0 || mNumberOfBackSeats > 0) {

                    int totalFrontSeatRate = mNumberOfSeats * Integer.parseInt(tripModel.getFront_seat_rate());
                    int totalBackSeatRate = mNumberOfBackSeats * Integer.parseInt(tripModel.getBack_seat_rate());
                    bookCarTrip(tripModel, mNumberOfSeats, mNumberOfBackSeats, totalFrontSeatRate + totalBackSeatRate);
                    dialog.dismiss();
                } else {
                    Toast.makeText(CarBookingActivity.this, "Please select a valid number of seats", Toast.LENGTH_SHORT).show();

                }

            }
        });
//        AlertDialog alert = builder.create();
//        alert.show();
        dialog.show();
    }

    @OnClick(R.id.backArrow)
    public void onViewClicked() {
        onBackPressed();
    }

    public class CarBookingAdapter extends RecyclerView.Adapter<CarBookingAdapter.MyViewHolder> {
        ArrayList<CarTripModel> bookingCollection;

        public CarBookingAdapter(ArrayList<CarTripModel> array) {
            this.bookingCollection = array;
        }

        public void append(CarTripModel item) {
            bookingCollection.add(item);
        }

        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_car_bookings, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            final CarTripModel dataObject = bookingCollection.get(position);
            holder.txtVendorName.setText(dataObject.getVendor() + "");
            holder.txtAvailbleSeats.setText((dataObject.getBack_seat() + dataObject.getFront_seat()) + "");
            int totalFrontSeatRate = dataObject.getFront_seat() * Integer.parseInt(dataObject.getFront_seat_rate());
            int totalBackSeatRate = dataObject.getBack_seat() * Integer.parseInt(dataObject.getBack_seat_rate());
            holder.txtTotalPrice.setText((totalBackSeatRate + totalFrontSeatRate) + "");
            holder.txtCarModel.setText(dataObject.getVehicle().getName());
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
