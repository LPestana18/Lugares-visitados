package br.com.lucaspestana.lugares_visitados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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