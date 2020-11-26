package com.example.smarthome.relays.services;

import com.example.smarthome.relays.models.Relay;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRelayRetrofitService {

    @POST("/relays")
    Call<JsonObject> add(@Body Relay relay);

    @DELETE("/relays/{id}")
    Call<JsonObject> deleteById(@Path("id") Long id);

    @GET("/relays")
    Call<List<Relay>> getAll();

    // TODO implement observable
/*    @GET("/relays")
    Observable<List<Relay>> getRelaysObservable();*/

    @GET("/relays/{id}")
    Call<Relay> getById(@Path("id") Long id);

    @GET("/relays/ip/{ip}")
    Call<JsonObject> getByIp(@Path("ip") String ip);

    @PUT("/relays/{id}")
    Call<JsonObject> updateById(@Path("id") Long id, Relay relay);

    @POST("/relays/{id}/turn")
    Call<ResponseBody> turn(@Path("id") Long id);

}
