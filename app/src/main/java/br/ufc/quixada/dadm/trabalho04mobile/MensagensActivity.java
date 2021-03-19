package br.ufc.quixada.dadm.trabalho04mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MensagensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        verifyAuthentication();
    }

    private void verifyAuthentication(){
        if (FirebaseAuth.getInstance().getUid() == null){
            Intent i = new Intent(MensagensActivity.this, LoginActivity.class);

            i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contatos:
                Intent i = new Intent(MensagensActivity.this, ContatosActivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                verifyAuthentication();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}