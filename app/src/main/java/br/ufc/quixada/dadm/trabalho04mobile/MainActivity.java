package br.ufc.quixada.dadm.trabalho04mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText etLogin;
    private EditText etSenha;
    private Button btnEnter;
    private TextView txtCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = findViewById(R.id.editTextLogin);
        etSenha = findViewById(R.id.editTextSenha);
        btnEnter = findViewById(R.id.buttonLogin);
        txtCadastro = findViewById(R.id.textViewCadastro);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString();
                String senha = etSenha.getText().toString();

                Log.i("Teste", login);
                Log.i("Teste", senha);
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Cadastro.class);
                startActivity(i);
            }
        });
    }

}