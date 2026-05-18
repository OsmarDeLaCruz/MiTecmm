package com.example.mitecmm.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.network.api.HorariosApi;
import com.example.mitecmm.network.client.SupabaseClient;
import com.example.mitecmm.network.response.HorariosRemote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorariosRepository {
    Context context;

    public HorariosRepository(Context context){
        this.context = context;
    }

    public void sincronizar(){
        HorariosApi api = SupabaseClient.getClient().create(HorariosApi.class);
        api.obtenerHorarios().enqueue(new Callback<List<HorariosRemote>>() {
            @Override
            public void onResponse(Call<List<HorariosRemote>> call, Response<List<HorariosRemote>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<HorariosRemote> datos = response.body();

                    new Thread(() -> {
                        DatabaseHelper helper = new DatabaseHelper(context);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.beginTransaction();

                        try {
                            for(HorariosRemote h : datos){
                                ContentValues values = new ContentValues();
                                values.put("idRemoto", h.getId());
                                values.put("docente", h.getDocente());
                                values.put("materia", h.getMateria());
                                values.put("grupo", h.getGrupo());
                                values.put("dia", h.getDia());
                                values.put("horaInicio", h.getHora_inicio());
                                values.put("horaFin", h.getHora_fin());
                                values.put("aula", h.getAula());

                                db.insertWithOnConflict("horarios_docentes", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                            }
                            db.setTransactionSuccessful();
                        } finally {
                            db.endTransaction();
                            db.close();
                        }

                        Log.d("SUPABASE", "Horarios Sincronizados");
                    }).start();

                }
            }

            @Override
            public void onFailure(Call<List<HorariosRemote>> call, Throwable t) {
                Log.e("SUPABASE", "Error al sincronizar horarios: "+t.getMessage());
            }
        });
    }

    public void actualizarHorario(int id, String docente, String materia, String grupo, String dia, String horaInicio, String horaFin, String aula, Runnable onExito){
        HorariosApi api = SupabaseClient.getClient().create(HorariosApi.class);

        Map<String, Object> body = new HashMap<>();
        body.put("docente", docente);
        body.put("materia", materia);
        body.put("grupo", grupo);
        body.put("dia", dia);
        body.put("hora_inicio", horaInicio);
        body.put("hora_fin", horaFin);
        body.put("aula", aula);

        api.actualizarHorarios("eq."+id, body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("SUPABASE", "Horario actualizado: id="+id);
                    sincronizar();
                    if(onExito != null) onExito.run();
                } else {
                    Log.e("SUPABASE", "Error al actualizar: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SUPABASE", "Fallo al actualizar: "+t.getMessage());
            }
        });
    }

    public void eliminarHorario(int id, Runnable onExito){
        HorariosApi api = SupabaseClient.getClient().create(HorariosApi.class);
        api.eliminarHorarios("eq."+id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("SUPABASE","Horario eliminado: id="+id);
                    sincronizar();
                    if(onExito != null) onExito.run();
                }else {
                    Log.e("SUPABASE", "Error al eliminar: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SUPABASE", "Fallo al eliminar: "+t.getMessage());
            }
        });
    }
}
