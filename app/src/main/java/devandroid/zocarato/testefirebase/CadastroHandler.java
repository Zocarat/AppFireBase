package devandroid.zocarato.testefirebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CadastroHandler{
    private Context context;
    private static final String TAG = "CadastroHandler";

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    public CadastroHandler(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        int o = 0;
    }

    public void cadastrarUsuario(String nome, String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Cadastro do usuário realizado com sucesso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "createUserWithEmail:success");
                        // updateUI(user);
                    } else {
                        // Ocorreu um erro ao cadastrar o usuário
                        String errorMessage = task.getException().getMessage();
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(context, "Erro ao cadastrar usuário: " + errorMessage, Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }
                });
    }

}
