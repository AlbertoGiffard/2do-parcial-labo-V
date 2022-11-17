package com.example.dialog;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPConexionInternet {

    public String consultarUsuarios(String endpoint){
        String resultado = null;
        try {

            URL url = new URL(endpoint);
            //agregar el cast
            //agregar el segundo exception
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //le digo de que tipo sera
            connection.setRequestMethod("GET");
            connection.connect();
            //obtener el codigo de respuesta (200,400,500)
            int respuestaCodigo = connection.getResponseCode();

            if (respuestaCodigo == 200){
                //no es la respuesta como tal, es un enlace a la respuesta que aun está en el servidor
                InputStream is = connection.getInputStream();
                //sacamos con un output
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cantidadLeido = 0;

                //cuando devuelva -1 es que no hay mas datos por leer
                //debe estar esto dentro del while
                while ((cantidadLeido = is.read(buffer)) != -1){
                    baos.write(buffer, 0, cantidadLeido);
                }
                //para cerrar la conexion
                is.close();

                return new String(baos.toByteArray(), "UTF-8");
            } else {
                //no es necesario que sea una exception
                throw new IOException();
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  resultado;
    }

    public byte[] consultarImg(String endpoint){
        byte[] resultado = null;
        try {
            URL url = new URL(endpoint);
            //agregar el cast
            //agregar el segundo exception
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //le digo de que tipo sera
            connection.setRequestMethod("GET");
            connection.connect();
            //obtener el codigo de respuesta (200,400,500)
            int respuestaCodigo = connection.getResponseCode();

            if (respuestaCodigo == 200){
                //no es la respuesta como tal, es un enlace a la respuesta que aun está en el servidor
                InputStream is = connection.getInputStream();
                //sacamos con un output
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cantidadLeido = 0;

                //cuando devuelva -1 es que no hay mas datos por leer
                //debe estar esto dentro del while
                while ((cantidadLeido = is.read(buffer)) != -1){
                    baos.write(buffer, 0, cantidadLeido);
                }
                //para cerrar la conexion
                is.close();

                return baos.toByteArray();
            } else {
                //no es necesario que sea una exception
                throw new IOException();
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  resultado;
    }
}
