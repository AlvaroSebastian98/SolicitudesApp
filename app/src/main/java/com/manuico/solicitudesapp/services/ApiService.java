package com.manuico.solicitudesapp.services;

import com.manuico.solicitudesapp.models.ResponseMessage;
import com.manuico.solicitudesapp.models.Solicitud;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    //String API_BASE_URL = "http://localhost:8089/productos/";
    String API_BASE_URL = "http://10.200.171.241:8089";

    @GET("/solicitudes")
    Call<List<Solicitud>> getSolicitudes();

    @GET("/solicitudes/{id}")
    Call<List<Solicitud>> showOwnSolicitudes(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("/solicitudes")
    Call<ResponseMessage> createSolicitud(@Field("idUsuario") Integer id_usuario,
                                         @Field("correo") String correo,
                                         @Field("tipoSolicitud") String tipo_solicitud,
                                         @Field("motivo") String motivo);
    @Multipart
    @POST("/solicitudes")
    Call<ResponseMessage> createSolicitudWithImage(
            @Part("idUsuario") RequestBody id_usuario,
            @Part("correo") RequestBody correo,
            @Part("tipoSolicitud") RequestBody tipo_solicitud,
            @Part("motivo") RequestBody motivo,
            @Part MultipartBody.Part imagen
    );

}
