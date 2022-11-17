package com.example.dialog;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class HiloConexion implements Runnable{
    public static final int IMG = 10;
    public static final int TEXT = 5;
    Handler handler;
    boolean img;
    private String urlImg;


    public HiloConexion(Handler handler, boolean img){
        this.handler = handler;
        this.img = img;
    }


    public HiloConexion(Handler handler, boolean img, String urlImg){
        this.handler = handler;
        this.img = img;
        this.urlImg = urlImg;
    }

    @Override
    public void run(){
        //no se puede conectar a internet en el hilo principal
        HTTPConexionInternet http = new HTTPConexionInternet();

        if (img) {
            byte[] respuesta = http.consultarImg(urlImg);

            Message message = new Message();
            message.obj = respuesta;
            message.arg1 = IMG;
            handler.sendMessage(message);
        } else {
            //https://catfact.ninja/fact
            //http://10.112.1.36:3001/usuarios
            String respuesta = http.consultarUsuarios("https://6355acf4da523ceadc05bdbf.mockapi.io/users");

            Log.d("Respuesta", respuesta);
            Message message = new Message();
            //xml
            //message.obj = Parser.xmlToList(respuesta);
            ObjectApi.getResultsInJson(respuesta);
            message.obj = respuesta;
            message.arg1 = TEXT;
            handler.sendMessage(message);
        }
    }

}
