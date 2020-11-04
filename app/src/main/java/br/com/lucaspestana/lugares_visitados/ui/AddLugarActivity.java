package br.com.lucaspestana.lugares_visitados.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.lucaspestana.lugares_visitados.Classes.Lugar;
import br.com.lucaspestana.lugares_visitados.R;
import br.com.lucaspestana.lugares_visitados.gps.GpsActivity;

public class AddLugarActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference("lugares");
    private FirebaseAuth mAuth;
    EditText nome;
    EditText descricao;
    EditText latitude;
    EditText longitude;
    Button localizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lugar);

        mAuth = FirebaseAuth.getInstance();

        Button Addlugar = findViewById(R.id.button_Addlugar);
        Button cancelar = findViewById(R.id.button_cancelar);
        nome = findViewById(R.id.input_name);
        descricao = findViewById(R.id.input_description);
        latitude = findViewById(R.id.input_latitude);
        longitude = findViewById(R.id.input_longitude);
        localizacao = findViewById(R.id.textView_localizacao);

        Addlugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser userId = mAuth.getCurrentUser();
                Lugar lugar = new Lugar(nome.getText().toString(), descricao.getText().toString(),
                        latitude.getText().toString(), longitude.getText().toString());
                gravaLugar(userId.getUid(), lugar);
                esconderTeclado(v);
                limparCampos();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        localizacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                entrarGps();
            }

        });
    }

    private void gravaLugar(String userId, Lugar lugar) {
        myRef.child(userId).setValue(lugar);
    }

    private void esconderTeclado(View view) {
        InputMethodManager ims = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ims.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void limparCampos() {
        nome.setText("");
        descricao.setText("");
        latitude.setText("");
        longitude.setText("");
        getCurrentFocus().clearFocus();
    }

    private void entrarGps(){
        startActivity(new Intent(this, GpsActivity.class));
    }
}