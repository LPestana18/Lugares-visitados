package br.com.lucaspestana.lugares_visitados.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.lucaspestana.lugares_visitados.R;

public class MainActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText senhaEditText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = findViewById(R.id.loginEditText);
        senhaEditText = findViewById(R.id.senhaEditText);
        auth = FirebaseAuth.getInstance();
    }

    public void irParaCadastro(View view) {
        startActivity(new Intent(this, NovoUsuarioActivity.class));
    }

    public void criarNovoUsuario (View view) {
        String login = loginEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();
        auth.signInWithEmailAndPassword(login, senha).addOnSuccessListener((result) -> {
          startActivity(new Intent(this, ListaLugaresActivity.class));
        }).addOnFailureListener((error) -> {
            error.printStackTrace();
        });
    }

    public void fazerLogin(View view) {
        String login = loginEditText.getEditableText().toString();
        String senha =  senhaEditText.getEditableText().toString();
        auth.signInWithEmailAndPassword(login, senha).addOnSuccessListener((result)-> {
            startActivity(new Intent(this, ListaLugaresActivity.class));
        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}