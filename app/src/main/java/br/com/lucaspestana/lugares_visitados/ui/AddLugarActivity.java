package br.com.lucaspestana.lugares_visitados.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.lucaspestana.lugares_visitados.Classes.Lugar;
import br.com.lucaspestana.lugares_visitados.R;

public class AddLugarActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("lugares");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lugar);
    }

    private void novoLugar(String userId, String name, String descricao, String data, int latitude, int longitude) {
        Lugar lugar = new Lugar(name, descricao, data, latitude, longitude);

        myRef.child("lugares").child(userId).setValue(lugar);
    }
}