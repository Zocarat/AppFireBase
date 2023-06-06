package devandroid.zocarato.testefirebase.ui;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DatabaseHandler {
    private static final String TAG = "DatabaseHandler";

    private DatabaseReference databaseReference;

    public DatabaseHandler() {
        // Obtém a instância do Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Define a referência do banco de dados (neste exemplo, o caminho é "dados")
        databaseReference = firebaseDatabase.getReference("users");
    }

    public void enviarTexto(String texto) {
        // Envia o texto para o banco de dados
        databaseReference.push().setValue(texto, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // Dados salvos com sucesso
                    Log.d(TAG, "Dados salvos com sucesso");
                } else {
                    // Ocorreu um erro ao salvar os dados
                    Log.e(TAG, "Erro ao salvar os dados: " + databaseError.getMessage());
                }
            }
        });
    }

    public void cadastrarUsuario(String email, String senha) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Cadastro realizado com sucesso
                            Log.d(TAG, "Usuário cadastrado com sucesso");
                        } else {
                            // Ocorreu um erro no cadastro
                            Log.e(TAG, "Erro ao cadastrar usuário: " + task.getException().getMessage());
                           int i = 0;

                        }
                    }
                });
    }
}