package devandroid.zocarato.testefirebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginHandler {

    private FirebaseAuth mAuth;

    public LoginHandler() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void realizarLogin(String email, String senha, OnLoginListener listener) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        String errorMessage = task.getException().getMessage();
                        listener.onFailure(errorMessage);
                    }
                });
    }

    public interface OnLoginListener {
        void onSuccess(FirebaseUser user);

        void onFailure(String errorMessage);
    }
}

