package br.com.lucaspestana.lugares_visitados.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.OnItemLongClickListener;

import org.w3c.dom.Text;

import java.util.List;

import br.com.lucaspestana.lugares_visitados.Classes.Place;
import br.com.lucaspestana.lugares_visitados.R;

public class ListaLugaresActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddLugares);

        verifyAuthentication();

        RecyclerView rv = findViewById(R.id.recycler_lugares);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fetchPlaces();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irLugares();
            }
        });


        // click em um item da lista
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(ListaLugaresActivity.this, AddLugarActivity.class);
                startActivity(intent);
            }
        });

        // click longo em um item da lista
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull Item item, @NonNull View view) {
                return true;
            }
        });
    }

    private void fetchPlaces() {
        FirebaseFirestore.getInstance().collection("/lugares")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Teste", error.getMessage(), error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            Place place = doc.toObject(Place.class);
                            Log.d("Teste", place.getNome());

                            adapter.add(new PlaceItem(place));

                        }
                    }
                });
    }

    public void irLugares() {

        startActivity(new Intent(this, AddLugarActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent = new Intent(ListaLugaresActivity.this, AddLugarActivity.class);
                startActivity(intent);
                break;
            case R.id.sair:
                FirebaseAuth.getInstance().signOut();
                verifyAuthentication();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private void verifyAuthentication(){
        if (FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(ListaLugaresActivity.this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }

    private class PlaceItem extends Item<GroupieViewHolder> {

        private Place place;

        private PlaceItem(Place place) {
            this.place = place;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtName = viewHolder.itemView.findViewById(R.id.text_lugares_nome);
            TextView txtDescription = viewHolder.itemView.findViewById(R.id.text_lugares_descricao);
            TextView txtData = viewHolder.itemView.findViewById(R.id.text_lugares_data);
            TextView txtLat = viewHolder.itemView.findViewById(R.id.text_lugares_latitude);
            TextView txtLng = viewHolder.itemView.findViewById(R.id.text_lugares_longitude);

            txtName.setText(place.getNome());
            txtDescription.setText(place.getDescricao());
            txtData.setText(place.getData());
            txtLat.setText(place.getLat());
            txtLng.setText(place.getLon());

            Picasso.get()
                    .load(place.getNome());
        }

        @Override
        public int getLayout() {
            return R.layout.lugares_item;
        }
    }

}