package br.ufc.quixada.dadm.trabalho04mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Cadastro extends AppCompatActivity {

    private EditText etNome;
    private EditText etLogin;
    private EditText etSenha;
    private Button btnCadastrar;
    private Button btnImagem;
    private ImageView imgPhoto;
    
    private Uri mSelectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.editTextName);
        etLogin = findViewById(R.id.editTextLogin);
        etSenha = findViewById(R.id.editTextSenha);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
        btnImagem = findViewById(R.id.buttonImage);
        imgPhoto = findViewById(R.id.imageFoto);

        btnImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPhoto();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode , resultCode, data);

        if(requestCode == 0){
            mSelectedUri = data.getData();

            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedUri);
                imgPhoto.setImageDrawable(new BitmapDrawable(bitmap));
                btnImagem.setAlpha(0);
            } catch (IOException e){

            }
        }

    }

    //pega imagem da galeria
    private void selectedPhoto(){
        Intent i = new Intent (Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }

    private void createUser(){
        String nome = etNome.getText().toString();
        String login = etLogin.getText().toString();
        String senha = etSenha.getText().toString();

        if(nome == null || nome.isEmpty() ||login == null || login.isEmpty() || senha == null || senha.isEmpty()){
            Toast.makeText(this, "Nome, senha e email devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Log.i("Teste", task.getResult().getUser().getUid());

                            saveUserInFirebase();

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }

    private void saveUserInFirebase(){
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(mSelectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Teste", uri.toString());

                                String uid = FirebaseAuth.getInstance().getUid();
                                String login = etLogin.getText().toString();
                                String profileURL = uri.toString();

                                User user = new User(uid, login, profileURL);

                                FirebaseFirestore.getInstance().collection("users")
                                    .add(user)
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
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Teste", e.getMessage(), e);
                    }
                });
    }
}