package com.example.listaenemigosjahel;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListaPersistente {

    ArrayList<ObjetoOfensa> listaDeOfensas;

    public ListaPersistente() {
        listaDeOfensas = new ArrayList<>();
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public ListaPersistente fromJson(String json) {
        Gson gson = new Gson();
        ListaPersistente listaPersistente = gson.fromJson(json, ListaPersistente.class);
        return listaPersistente;
    }
}