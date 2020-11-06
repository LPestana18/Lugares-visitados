package br.com.lucaspestana.lugares_visitados.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.lucaspestana.lugares_visitados.Classes.Place;
import br.com.lucaspestana.lugares_visitados.R;
import br.com.lucaspestana.lugares_visitados.gps.GpsActivity;

public class AddLugarActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditDescription;
    private TextView mTextlocation;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lugar);

        Button AddPlace = findViewById(R.id.button_Addplace);
        Button cancel = findViewById(R.id.button_cancel);
        mEditName = findViewById(R.id.input_name);
        mEditDescription = findViewById(R.id.input_description);
        mTextlocation = findViewById(R.id.textView_location);



        AddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlace();
                hideKeyboard(v);
                clearFields();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        mTextlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterGps();
            }

        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager ims = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ims.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void clearFields() {
        mEditName.setText("");
        mEditDescription.setText("");
        getCurrentFocus().clearFocus();
    }

    private void createPlace () {
        String name = mEditName.getText().toString();
        String description = mEditDescription.getText().toString();

        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            Toast.makeText(this, "Nome e descrição devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        Place place = new Place(name, description);

        db.collection("lugares")
                .add(place)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });

    }

    private void enterGps(){
        startActivity(new Intent(this, GpsActivity.class));
    }

}