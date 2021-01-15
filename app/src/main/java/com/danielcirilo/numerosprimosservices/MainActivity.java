package com.danielcirilo.numerosprimosservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListadoPrimos;
    private Button btConsultar;
    private TextView tvCantidadPrimos;

    private NumerosPrimosService myService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListadoPrimos = findViewById(R.id.rvListaPrimos);
        btConsultar = findViewById(R.id.btConsultar);
        tvCantidadPrimos = findViewById(R.id.tvCantidadPrimos);
        Intent intent = new Intent(this, NumerosPrimosService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCantidadPrimos.setText(String.valueOf(myService.getNumerosPrimos().size()));
                rvListadoPrimos.setAdapter(new PrimosAdapter(myService.getNumerosPrimos(), MainActivity.this));
                rvListadoPrimos.setHasFixedSize(true);
                rvListadoPrimos.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
            }
        });
    }
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NumerosPrimosService.LocalBinder binder = (NumerosPrimosService.LocalBinder)service;
            myService = binder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}