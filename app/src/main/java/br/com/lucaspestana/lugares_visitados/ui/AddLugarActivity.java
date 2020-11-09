package br.com.lucaspestana.lugares_visitados.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.lucaspestana.lugares_visitados.Classes.Place;
import br.com.lucaspestana.lugares_visitados.R;

public class AddLugarActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditDescription;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Double latitudeAtual;
    private Double longitudeAtual;

    private static final int REQUEST_CODE_GPS = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lugar);

        Button AddPlace = findViewById(R.id.button_Addplace);
        Button cancel = findViewById(R.id.button_cancel);
        mEditName = findViewById(R.id.input_name);
        mEditDescription = findViewById(R.id.input_description);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                latitudeAtual = lat;
                longitudeAtual = lon;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        //a permissão já foi dada?
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //somente ativa
            //a localização é obtida via hardware, intervalo de 0
            //segundos e 0 metros entre atualizações
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        } else {
            //permissão ainda não foi nada, solicita ao usuário
            //quando o usuário responder, o método
            //onRequestPermissionsResult vai ser chamado
            ActivityCompat.requestPermissions(this,
                    new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                //permissão concedida, ativamos o GPS
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            } else {
                //usuário negou, não ativamos
                Toast.makeText(this, getString(R.string.no_gps_no_app),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
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

    private void createPlace() {
        String name = mEditName.getText().toString();
        String description = mEditDescription.getText().toString();
        String date = getDateTime();
        String lat = latitudeAtual.toString();
        String lon = longitudeAtual.toString();

        if (name == "" || name.isEmpty() || description == "" || description.isEmpty()) {
            Toast.makeText(this, "Nome e descrição devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }
        Place place = new Place(name, description, date, lat, lon);
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

    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}