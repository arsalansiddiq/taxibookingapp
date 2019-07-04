package com.taxibooking.user.Retrofit;
/**
 * @Developer android
 * @Company android
 **/

import com.taxibooking.user.Helper.URLHelper;
import com.taxibooking.user.Models.BusTimeSlotModel;
import com.taxibooking.user.Models.BusTripModel;
import com.taxibooking.user.Models.CarTripModel;
import com.taxibooking.user.Models.CitiesModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    //synchronous.
    @GET("json?")
    Call<ResponseBody> getResponse(@Query("latlng") String param1, @Query("key") String param2);

    @POST(URLHelper.EXTEND_TRIP)
    @FormUrlEncoded
    Call<ResponseBody> extendTrip(@Header("X-Requested-With") String xmlRequest, @Header("Authorization") String strToken,
                                  @Field("request_id") String request_id, @Field("latitude") String latitude, @Field("longitude") String longitude, @Field("address") String address);

    @GET(URLHelper.SAVE_LOCATION)
    Call<ResponseBody> getFavoriteLocations(@Header("X-Requested-With") String xmlRequest,
                                            @Header("Authorization") String strToken);

    @POST(URLHelper.SAVE_LOCATION)
    @FormUrlEncoded
    Call<ResponseBody> updateFavoriteLocations(@Header("X-Requested-With") String xmlRequest, @Header("Authorization") String strToken,
                                               @Field("type") String type, @Field("latitude") String latitude, @Field("longitude") String longitude, @Field("address") String address);

    @DELETE(URLHelper.SAVE_LOCATION + "/" + "{id}")
    Call<ResponseBody> deleteFavoriteLocations(@Path("id") String id, @Header("X-Requested-With") String xmlRequest, @Header("Authorization") String strToken,
                                               @Query("type") String type, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("address") String address);


    //regions Booking Module

    @GET(URLHelper.GET_ALL_CITIES)
    Call<ArrayList<CitiesModel>> getAllCities(@Header("Authorization") String strToken);

    @GET(URLHelper.GET_ALL_BUS_TIME_SLOT)
    Call<ArrayList<BusTimeSlotModel>> getAllBusTimeSlots(@Header("Authorization") String strToken,
                                                         @Query("date") String date);


    @GET(URLHelper.GET_ALL_CAR_TRIPS)
    Call<ArrayList<CarTripModel>> getCarTrips(@Header("Authorization") String strToken,
                                              @Query("from_city_id") String from_city_id, @Query("to_city_id") String to_city_id);


    @GET(URLHelper.GET_ALL_BUS_TRIPS)
    Call<ArrayList<BusTripModel>> getBusTrips(@Header("Authorization") String strToken,
                                              @Query("from_city_id") String from_city_id, @Query("to_city_id") String to_city_id,
                                              @Query("date") String date, @Query("time") String time);


    @POST(URLHelper.POST_CAR_BOOKING)
    @FormUrlEncoded
    Call<Void> postCarBooking(@Header("Authorization") String strToken,
                                      @Field("car_trip_id") String car_trip_id, @Field("name") String name,
                                      @Field("number") String number, @Field("from_city_id") String from_city_id,
                                      @Field("to_city_id") String to_city_id, @Field("date") String date,
                                      @Field("front_seat") String front_seat, @Field("back_seat") String back_seat,
                                      @Field("total") String total);

    @POST(URLHelper.POST_BUS_BOOKING)
    @FormUrlEncoded
    Call<Void> postBusBooking(@Header("Authorization") String strToken,
                                      @Field("bus_trip_id") String car_trip_id, @Field("name") String name,
                                      @Field("number") String number, @Field("from_city_id") String from_city_id,
                                      @Field("to_city_id") String to_city_id, @Field("date") String date,
                                      @Field("time") String time, @Field("booking_seat") String booking_seat,
                                      @Field("total") String total);

    //endregion


}
