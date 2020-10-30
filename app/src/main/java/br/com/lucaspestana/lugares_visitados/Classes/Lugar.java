package br.com.lucaspestana.lugares_visitados.Classes;

import android.widget.EditText;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Lugar {
    public String nome;
    public String descricao;
    public String data;
    public String latitude;
    public  String longitude;

    public Lugar(EditText nome, EditText descricao, EditText latitude, EditText longitude) {
        // Default constructor required for calls to DataSnapshot.getValue(Lugar.class)
    }

    public Lugar(String nome, String descricao, String latitude, String longitude) {
        this.nome = nome;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
