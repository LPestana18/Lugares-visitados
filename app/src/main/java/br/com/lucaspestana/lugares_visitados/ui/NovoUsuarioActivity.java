package br.com.lucaspestana.lugares_visitados.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.lucaspestana.lugares_visitados.Classes.User;
import br.com.lucaspestana.lugares_visitados.R;

public class NovoUsuarioActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button mButtonRegister;
    private Button mButtonCancel;
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mEditConfPassword;
    private Uri mSelectedUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        mButtonRegister = findViewById(R.id.btnCadastrar);
        mButtonCancel = findViewById(R.id.btnCancel);
        mEditName = findViewById(R.id.edNome);
        mEditEmail = findViewById(R.id.edEmail);
        mEditPassword = findViewById(R.id.edSenha);
        mEditConfPassword = findViewById(R.id.edConfSenha);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditName.setText("");
                mEditEmail.setText("");
                mEditPassword.setText("");
                mEditConfPassword.setText("");
                getCurrentFocus().clearFocus();
            }
        });
    }

    private void createUser() {
        String name = mEditName.getText().toString();
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        String confPassword = mEditConfPassword.getText().toString();

        if(name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()
                || confPassword == null || confPassword.isEmpty()){
            Toast.makeText(this, "Nome, Email e senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i("Teste", task.getResult().getUser().getUid());

                            saveUserInFirebase();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
            }
        });
    }

    private void saveUserInFirebase() {
        String name = mEditName.getText().toString();
        String email = mEditEmail.getText().toString();

        User user = new User(name, email);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste", documentReference.getId());
                        Intent intent = new Intent(NovoUsuarioActivity.this, ListaLugaresActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }
}