package com.danielcirilo.numerosprimosservices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;


import java.util.ArrayList;

public class NumerosPrimosService extends Service {


    private IBinder binder = new LocalBinder();
    private Thread thread;
    private ArrayList<Integer> numerosPrimos;
    @Override
    public void onCreate() {
        super.onCreate();
        numerosPrimos = new ArrayList<>();
        thread = new Thread(){
            @Override
            public void run(){
                int primos = 2;

                int contador = 0;

                while (true) { /**No es la mejor manera de manejar un servicio, Ya que podemos tener problemas si lo deeamos parar
                                es mejor controlarlo con una variable de tipo booleano. Y poner condiciones si deseamos pararla.**/
                    if (primos > 2) {
                        primos += 2;
                    }

                    for (int i = 1; i <= primos; i++) {

                        if (primos % i == 0) {
                            contador++;
                        }
                    }

                    if (contador == 2) {
                        numerosPrimos.add(primos);

                    }

                    if (primos == 2){
                        primos++;
                    }

                    contador = 0;
                }
            }
        };
        thread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder{
        public NumerosPrimosService getService(){
            return NumerosPrimosService.this;
        }
    }

    public ArrayList<Integer> getNumerosPrimos() {
        return numerosPrimos;
    }
}