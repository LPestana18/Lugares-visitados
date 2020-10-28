package br.com.lucaspestana.lugares_visitados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ListaLugaresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares);
    }

    public void irParaCadastroLugares() {
        startActivity(new Intent(this, AddLugarActivity.class));
    }
}