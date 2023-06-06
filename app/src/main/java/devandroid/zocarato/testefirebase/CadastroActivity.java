package devandroid.zocarato.testefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import devandroid.zocarato.testefirebase.ui.DatabaseHandler;

public class CadastroActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editTextNome.getText().toString();
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();
                cadastrarUsuario(nome, email, senha);
            }
        });
    }

    private void cadastrarUsuario(String nome, String email, String senha) {
        CadastroHandler cadastroHandler = new CadastroHandler(CadastroActivity.this);
        cadastroHandler.cadastrarUsuario(nome, email, senha);
    }
}