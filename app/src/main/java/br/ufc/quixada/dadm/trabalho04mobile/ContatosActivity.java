package br.ufc.quixada.dadm.trabalho04mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class ContatosActivity extends AppCompatActivity {
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatos);
        RecyclerView rv = findViewById(R.id.recyclerView);

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //busca usuarios no firebase

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent i = new Intent(ContatosActivity.this, ChatActivity.class);

                UserItem userItem = (UserItem) item;
                i.putExtra("user", userItem.user);

                startActivity(i);
            }
        });
        fetchUsers();
    }

    private void fetchUsers(){
        FirebaseFirestore.getInstance().collection("/users")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(e != null){
                        Log.i("Teste", e.getMessage());
                        return;
                    }

                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : docs){
                        User user = doc.toObject(User.class);
                        Log.i("Teste", user.getLogin());

                        adapter.add(new UserItem(user));
                    }
                }
            });
    }
    // classe interna que é responsável por gerenciar cada item da classe
    private class UserItem extends Item<ViewHolder> {

        private final User user;

        private UserItem(User user) {
            this.user = user;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView textUsername = viewHolder.itemView.findViewById(R.id.textViewUsername);
            ImageView imgFoto = viewHolder.itemView.findViewById(R.id.imageViewPhoto);

            textUsername.setText(user.getLogin());

            Picasso.get()
                    .load(user.getProfileURL())
                    .into(imgFoto);
        }

        @Override
        public int getLayout() {
            return R.layout.item_user;
        }
    }
}