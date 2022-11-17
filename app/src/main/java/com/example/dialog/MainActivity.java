package com.example.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener {
    private Handler handler;
    public static String contacts;
    public static List<ObjectApi> objectApiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("information", Context.MODE_PRIVATE);
        //obtener los valores
        //le obtengo valor por defecto por si nunca se seteo
        MainActivity.contacts = sp.getString("contactos", "Sin nada cargado");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Segundo Parcial");

        //para que lea la cola de los mensajes y hay que implementar el handler
        //se dbe implementar antes que el hilo
        this.handler = new Handler(this);
        Log.d("datos",MainActivity.contacts);
        //si la lista está vacia le pega al get
        if (MainActivity.contacts.equals("Sin nada cargado")){
            Thread t1 = new Thread(new HiloConexion(handler, false));
            t1.start();
        } else {
            ObjectApi.getResultsInJson(MainActivity.contacts);
            TextView tvText = findViewById(R.id.tvText);
            tvText.setText(MainActivity.contacts);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        //search
        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        //

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnAdd){
            Ventana ventana = new Ventana(this);
            ventana.show(getSupportFragmentManager(), "ventana");
        }
        //MANIFEST
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PermissionChecker.PERMISSION_GRANTED){
            Log.d("ClickItem", "Se hizo click en llamada");
            //un intent que inicia una llamada
            Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:11"));
            //se hace startactiviytyresult para que traiga un resultado
            this.startActivity(intentCall);

        } else {
            //si no tiene el permiso, se lo pido
            //el request code es para mapear de que permiso es
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } */

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        //HILOS
        if (message.arg1 == HiloConexion.TEXT) {
            //SHARED PREFERENCES
            //guardar datos del usuario basicos
            //Mode private: solo esta aplicacion accede a la informacion
            SharedPreferences sp = getSharedPreferences("information", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("contactos", message.obj.toString());
            //ed.putInt("edad", 34);
            //guardarlo, sin esto, no persiste
            ed.commit();

            contacts = message.obj.toString();
            TextView tvText = findViewById(R.id.tvText);
            tvText.setText(MainActivity.contacts);
            //JSONArray jsonArray = new JSONArray(message.obj.toString());
            //xml
            /*List<Rss> n = (ArrayList<Rss>)message.obj;
            tvRespuesta.setText(n.get(0).descripcion);
            tvTitulo.setText(n.get(0).titulo);

            Thread t2 = new Thread(new HiloConexion(handler, true, n.get(0).urlImg));
            t2.start();*/

        } else if(message.arg1 ==HiloConexion.IMG){
            /*ImageView img = findViewById(R.id.img);
            byte[] imgByte = (byte[]) message.obj;

            img.setImageBitmap(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));*/

        }
        return false;
    }
    //MANIFEST
    @Override
    public boolean onQueryTextSubmit(String query) {
        Boolean result = false;
        int counter;

        //espera el enter del search
        Log.d("querysubmit", String.valueOf(MainActivity.objectApiList.size()));
        for (counter = 0; counter < MainActivity.objectApiList.size(); counter++) {
            if (MainActivity.objectApiList.get(counter).getUsername().equals(query)){
                //lo encuentra
                result = true;
                break;
            }
        }

        if (result) {
            String message = "El rol del usuario es " + MainActivity.objectApiList.get(counter).getRole();
            this.alertView(message, "Usuario encontrado");
        } else {
            String message = "El usuario " + query + " no esta dentro de la lista";
            this.alertView(message, "Usuario no encontrado");
        }

        return result;
    }
    //MANIFEST
    @Override
    public boolean onQueryTextChange(String newText) {
        //cada caratcter que llega
        //Log.d("querytext", newText);
        return false;
    }

    public void alertView( String message, String title ) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CERRAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}