package com.example.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClickDialogo implements DialogInterface.OnClickListener {
    View view;
    MainActivity mainActivity;
    //ctrl O
    public ClickDialogo() {
    }
    public ClickDialogo(View view, MainActivity mainActivity) {
        this.view = view;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_NEGATIVE){
            Log.d("Click", "En el negativo");
        } else if(i == DialogInterface.BUTTON_NEUTRAL){
            Log.d("Click", "En el neutral");
        } else {
            Log.d("Click", "En el positivo");
            if(this.validateField()){
                this.addAndRefreshList();
            }
        }
    }

    public Boolean validateField(){
        Boolean result = true;
        EditText editText = (EditText) this.view.findViewById(R.id.edUsername);
        String values = editText.getText().toString();

        if(TextUtils.isEmpty(values)) {
            result = false;
            this.alertView("El campo username no puede estar vacio");
        }

        return result;
    }

    private ObjectApi generateObject(){
        Integer id = MainActivity.objectApiList.get(MainActivity.objectApiList.size()).getId();
        EditText editText = (EditText) this.view.findViewById(R.id.edUsername);
        String valueEditText = editText.getText().toString();
        Spinner spRole = (Spinner) this.view.findViewById(R.id.spRole);
        String spRoleText = spRole.getSelectedItem().toString();
        ToggleButton toggleButton = ((ToggleButton) this.view.findViewById(R.id.tlBtn));
        ObjectApi objectApi = new ObjectApi(id++, valueEditText, spRoleText, toggleButton.isChecked());

        return objectApi;
    }

    public void addAndRefreshList() {
        ObjectApi objectApi = this.generateObject();
        MainActivity.objectApiList.add(objectApi);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < MainActivity.objectApiList.size(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", MainActivity.objectApiList.get(i).getId());
                jsonObject.put("username", MainActivity.objectApiList.get(i).getUsername());
                jsonObject.put("rol", MainActivity.objectApiList.get(i).getRole());
                jsonObject.put("admin", MainActivity.objectApiList.get(i).getAdmin());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
        }

        MainActivity.contacts = jsonArray.toString();
        TextView tvText = this.mainActivity.findViewById(R.id.tvText);
        tvText.setText(MainActivity.contacts);

        SharedPreferences sp = this.mainActivity.getSharedPreferences("information", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("contactos", MainActivity.contacts);
        ed.commit();
    }

    public void alertView( String message ) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.mainActivity).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
