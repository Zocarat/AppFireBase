package devandroid.zocarato.testefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView textViewUserEmail;
    private ImageButton imageButton;
    private Button buttonNovaMensagem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        imageButton = findViewById(R.id.imageButtonMessage);
        buttonNovaMensagem1 = findViewById(R.id.buttonIniciarConversa);



        // Obtenha o usuário atualmente logado
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Exiba o email do usuário na TextView
            String userEmail = user.getEmail();
            String login = userEmail;
            Global.getInstance().setLogin(login);

            textViewUserEmail.setText(userEmail);





        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Obtenha o usuário atualmente logado
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // Exiba o email do usuário na TextView
                        String userEmail = user.getEmail();

                        // Crie um Intent para abrir a ConversasActivity
                        Intent intent = new Intent(HomeActivity.this, ConversasActivity.class);
                        Intent intentGlobal = new Intent(HomeActivity.this, Global.class);

                        // Passe o endereço de e-mail como um extra na Intent
                        intent.putExtra("usuarioAtual", userEmail);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Erro ao abrir a tela de conversas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNovaMensagem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Crie um Intent para abrir a nova conversa
                Intent intent2 = new Intent(HomeActivity.this, CriarNovaConversaActivity.class);
                startActivity(intent2);
            }
        });


    }
    }
