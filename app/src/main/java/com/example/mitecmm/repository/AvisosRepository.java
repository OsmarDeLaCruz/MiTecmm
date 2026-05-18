package com.example.mitecmm.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.model.Aviso;
import com.example.mitecmm.network.api.AvisosApi;
import com.example.mitecmm.network.client.SupabaseClient;
import com.example.mitecmm.network.response.AvisoRemote;

import java.util.List;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AvisosRepository {
    private final DatabaseHelper dbHelper;

    public AvisosRepository(Context context){
        dbHelper  = new DatabaseHelper(context);
    }

    public void sincronizarAvisos(){
        AvisosApi api = SupabaseClient.getClient().create(AvisosApi.class);

        api.obtenerAvisos().enqueue(new Callback<List<AvisoRemote>>() {
            @Override
            public void onResponse(Call<List<AvisoRemote>> call, Response<List<AvisoRemote>> response) {
                Log.d("SUPABASE", "Respuesta recibida");
                if(response.isSuccessful() && response.body() != null){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    db.delete("avisos", null, null);
                    for(AvisoRemote remoto : response.body()){

                        Log.d("SUPABASE", remoto.getTitulo());

                        ContentValues values = new ContentValues();
                        if(remoto.getId() != null){
                            values.put("idRemoto", remoto.getId());
                        }
                        values.put("titulo", remoto.getTitulo());
                        values.put("descripcion", remoto.getContenido());
                        values.put("fecha", remoto.getFecha_publicacion());
                        values.put("categoria", remoto.isEs_urgente() ? "URGENTE" : "NORMAL");

                        db.insert("avisos", null, values);
                    }
                    db.close();
                }
            }



            @Override
            public void onFailure(Call<List<AvisoRemote>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void subirAviso(Aviso aviso){

        AvisosApi api = SupabaseClient.getClient().create(AvisosApi.class);

        AvisoRemote remote = new AvisoRemote();

        remote.setTitulo(aviso.getTitulo());

        remote.setContenido(aviso.getDescripcion());

        try {
            java.text.SimpleDateFormat origen = new java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new java.util.Locale("es"));
            java.text.SimpleDateFormat destino = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
            java.util.Date fecha = origen.parse(aviso.getFecha());
            remote.setFecha_publicacion(destino.format(fecha));
        }catch(Exception e){
            e.printStackTrace();
            remote.setFecha_publicacion("2026-05-14");
        }

        remote.setEs_urgente(aviso.getCategoria().equalsIgnoreCase("Urgente"));

        remote.setSubtitulo(aviso.getCategoria().toUpperCase() + ": " + aviso.getTitulo());

        api.insertarAviso(remote).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("SUPABASE","Código: " + response.code());

                if(response.isSuccessful()){
                    Log.d("SUPABASE", "Insert correcto");
                }else{
                    try{
                        Log.e("SUPABASE", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SUPABASE", t.getMessage());
            }
        });
    }

    public void eliminarAviso(int id){
        Log.d("SUPABASE", "Eliminando ID remoto: " + id);
        AvisosApi api = SupabaseClient.getClient().create(AvisosApi.class);
        api.eliminarAviso("eq."+id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("SUPABASE", "DELETE: "+response.code());
                try{
                    if(response.errorBody()!=null){
                        Log.e("SUPABASE", response.errorBody().string());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("SUPABASE", t.getMessage());
            }
        });
    }
}
