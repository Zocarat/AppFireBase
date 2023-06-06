package devandroid.zocarato.testefirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import devandroid.zocarato.testefirebase.ui.DatabaseHandler;
public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonLogin;
    private Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistro = findViewById(R.id.buttonRegistro);


        // Verificar se há informações salvas e preencher os campos
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String emailSalvo = sharedPreferences.getString("email", "");
        String senhaSalva = sharedPreferences.getString("senha", "");

        if (!emailSalvo.isEmpty()) {
            editTextEmail.setText(emailSalvo);
        }

        if (!senhaSalva.isEmpty()) {
            editTextSenha.setText(senhaSalva);
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Implemente a lógica de login aqui
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();
                // Salvar email e senha nas preferências compartilhadas
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("chave_email", email);
                editor.putString("chave_senha", senha);
                editor.apply();
                fazerLogin(email, senha);
            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implemente a lógica de registro aqui

                abrirCadastroActivity();
               // String email = editTextEmail.getText().toString();
                //String senha = editTextSenha.getText().toString();
                //fazerRegistro(email, senha);
            }
        });
    }

    private void fazerLogin(String email, String senha) {
        LoginHandler loginHandler = new LoginHandler();
        loginHandler.realizarLogin(email, senha, new LoginHandler.OnLoginListener() {
            @Override
            public void onSuccess(FirebaseUser user) {

                // Login bem-sucedido, você pode redirecionar para a próxima atividade ou realizar outras ações
                abrirHomeActivity();
            }

            @Override
            public void onFailure(String errorMessage) {
                // O login falhou, exiba uma mensagem de erro ou realize outras ações
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Opcional: encerra a atividade atual para que o usuário não possa retornar ao login pressionando o botão "Voltar"

    }

    private void fazerRegistro(String email, String senha) {
        // Implemente a lógica de registro aqui
    }

    private void abrirCadastroActivity() {
        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intent);

    }
}