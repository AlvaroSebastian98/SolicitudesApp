package com.manuico.solicitudesapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manuico.solicitudesapp.R;
import com.manuico.solicitudesapp.adapters.SolicitudesAdapter;
import com.manuico.solicitudesapp.models.Solicitud;
import com.manuico.solicitudesapp.services.ApiService;
import com.manuico.solicitudesapp.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView solicitudesList;

    private static final int REGISTER_FORM_REQUEST = 100;

    private Integer id = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitudesList = findViewById(R.id.recyclerview);
        solicitudesList.setLayoutManager(new LinearLayoutManager(this));

        solicitudesList.setAdapter(new SolicitudesAdapter());

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);


        //Código para mostrar SOLO las solicitudes de cada usuario

        Call<List<Solicitud>> call = service.showOwnSolicitudes(id);

        call.enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Solicitud> solicitudes = response.body();
                        Log.d(TAG, "solicitudes: " + solicitudes);

                        SolicitudesAdapter adapter = (SolicitudesAdapter) solicitudesList.getAdapter();
                        adapter.setSolicitudes(solicitudes);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        //Código para mostrar TODAS las solicitudes

        /*ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Solicitud>> call = service.getSolicitudes();

        call.enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Solicitud> solicitudes = response.body();
                        Log.d(TAG, "solicitudes: " + solicitudes);

                        SolicitudesAdapter adapter = (SolicitudesAdapter) solicitudesList.getAdapter();
                        adapter.setSolicitudes(solicitudes);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });*/
    }

    public void showRegister(View view){
        startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_FORM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            // refresh data
            initialize();
        }
    }
}
