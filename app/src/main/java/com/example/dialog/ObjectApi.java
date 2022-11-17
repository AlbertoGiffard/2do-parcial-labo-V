package com.example.dialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ObjectApi {
    private Integer id;
    private String username;
    private String role;
    private Boolean admin;

    public ObjectApi(Integer id, String username, String role, Boolean admin) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public static void getResultsInJson(String respuesta){
        if (MainActivity.objectApiList == null){
            MainActivity.objectApiList = new ArrayList<ObjectApi>();
        } else {
            MainActivity.objectApiList.clear();
        }

        try {
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray Objects = jsonObject.getJSONArray("objects");
            for (int i = 0; i < Objects.length(); i++) {
                JSONObject object = Objects.getJSONObject(i);
                Integer id = object.getInt("id");
                String username = object.getString("username");
                String role = object.getString("rol");
                Boolean admin = object.getBoolean("admin");

                ObjectApi newValue = new ObjectApi(id, username, role, admin);
                MainActivity.objectApiList.add(newValue);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
