package devandroid.zocarato.testefirebase;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CriarNovaConversaActivity extends AppCompatActivity{
    private EditText editTextDestinatario;
    private EditText editTextMensagem;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_nova_conversa);

        editTextDestinatario = findViewById(R.id.editTextDestinatario);
        editTextMensagem = findViewById(R.id.editTextMensagem);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destinatario = editTextDestinatario.getText().toString().trim();
                String mensagem = editTextMensagem.getText().toString().trim();
                String remetente = Global.getInstance().getLogin();

                // Obtém uma referência ao nó "conversas" no Firebase Realtime Database
                DatabaseReference conversasRef = FirebaseDatabase.getInstance().getReference("messages");

                // Cria uma nova chave única para a conversa
                String novaConversaKey = conversasRef.push().getKey();

                // Define os valores para ultimaMensagem e dataHoraUltimaMensagem
                  String conteudo = mensagem;   // Defina a última mensagem aqui
                  String data = "Timestamp";  // Defina o timestamp da última mensagem aqui

                // Cria um objeto "Conversa" com as informações da mensagem

                ConversaModel conversa = new ConversaModel(destinatario, remetente, conteudo, data);

                // Salva a conversa no Firebase Realtime Database
                conversasRef.child(novaConversaKey).setValue(conversa, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            // Ocorreu um erro ao salvar os dados
                            String errorMessage = databaseError.getMessage();
                            Toast.makeText(CriarNovaConversaActivity.this, "Erro ao salvar os dados: " + errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            // Os dados foram salvos com sucesso
                            Toast.makeText(CriarNovaConversaActivity.this, "Mensagem enviada para: " + destinatario, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                // Exemplo de exibição de uma mensagem de sucesso
                Toast.makeText(CriarNovaConversaActivity.this, "Mensagem enviada para: " + destinatario, Toast.LENGTH_SHORT).show();

                // Limpar os campos após o envio da mensagem
                editTextDestinatario.setText("");
                editTextMensagem.setText("");
            }
        });
    }


}