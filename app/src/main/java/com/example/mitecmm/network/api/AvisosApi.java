package com.example.mitecmm.network.api;

import com.example.mitecmm.network.response.AvisoRemote;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AvisosApi {
    @GET("avisos")
    Call<List<AvisoRemote>> obtenerAvisos();

    @POST("avisos")
    Call<Void> insertarAviso(
            @Body AvisoRemote aviso
    );

    @DELETE("avisos")
    Call<Void> eliminarAviso(
        @Query("id") String filtro
    );
}
