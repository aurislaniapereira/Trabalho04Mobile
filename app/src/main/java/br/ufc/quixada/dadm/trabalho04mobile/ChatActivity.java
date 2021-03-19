package br.ufc.quixada.dadm.trabalho04mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class ChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        User user = getIntent().getExtras().getParcelable("user");
        getSupportActionBar().setTitle(user.getLogin());

        RecyclerView rv = findViewById(R.id.recyclerChat);

        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));

    }

    private class MessageItem extends Item<ViewHolder> {
        private final boolean isLeft;

        private MessageItem(boolean isLeft) {
            this.isLeft = isLeft;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {

        }

        @Override
        public int getLayout() {
            return isLeft ? R.layout.item_from_message : R.layout.item_to_message;
        }
    }
}