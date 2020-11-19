package br.com.lucaspestana.lugares_visitados.Classes;

import com.google.firebase.firestore.Exclude;

public class Place {
    @Exclude private String id;
    private String nome;
    private String descricao;
    private String data;
    private String lat;
    private String lon;

    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(Lugar.class)
    }

    public Place(String nome, String descricao, String data, String lat, String lon) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
