package com.example.mitecmm.network.api;

import com.example.mitecmm.network.response.HorariosRemote;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface HorariosApi {

    @GET("horarios_docentes")
    Call<List<HorariosRemote>> obtenerHorarios();
    @GET("horarios_docentes")
    Call<List<HorariosRemote>> obtenerPorDocente(
            @Query("docente") String docente
    );
    @PATCH("horarios_docentes")
    Call<Void> actualizarHorarios(
            @Query("id") String filtroId,
            @Body Map<String, Object> campos
    );
    @DELETE("horarios_docentes")
    Call<Void> eliminarHorarios(
            @Query("id") String filtroId
    );

}
