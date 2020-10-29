package br.com.lucaspestana.lugares_visitados.Classes;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Lugar {
    public String nome;
    public String descricao;
    public String data;
    public int latitude;
    public  int longitude;

    public Lugar() {
        // Default constructor required for calls to DataSnapshot.getValue(Lugar.class)
    }

    public Lugar(String nome, String descricao, String data, int latitude, int longitude) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
