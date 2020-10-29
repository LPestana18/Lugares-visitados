package br.com.lucaspestana.lugares_visitados.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.lucaspestana.lugares_visitados.R;
import br.com.lucaspestana.lugares_visitados.ui.AddLugarActivity;

public class ListaLugaresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddLugares);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irLugares();
            }
        });
    }

    public void irLugares() {
        startActivity(new Intent(this, AddLugarActivity.class));
    }
}