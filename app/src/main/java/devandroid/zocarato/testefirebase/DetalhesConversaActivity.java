package devandroid.zocarato.testefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalhesConversaActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMensagens;
    private EditText editTextMensagem;
    private Button buttonEnviar;
    private List<Mensagem> mensagens;
    private String usuarioAtual;
    private String destinatario;
    private String tituloConversa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_conversa);

        recyclerViewMensagens = findViewById(R.id.recyclerViewMensagens);
        editTextMensagem = findViewById(R.id.editTextMensagem);
        buttonEnviar = findViewById(R.id.buttonEnviar);

        recyclerViewMensagens.setLayoutManager(new LinearLayoutManager(this));

        tituloConversa = getIntent().getStringExtra("tituloConversa");


        mensagens = new ArrayList<>();

        usuarioAtual = Global.getInstance().getLogin();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("conversa")) {
            ConversasActivity.Conversa conversa = (ConversasActivity.Conversa) intent.getSerializableExtra("conversa");
            if (conversa != null) {
                destinatario = conversa.getDestinatario();
                carregarMensagens();
            }
        }

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagem();
            }
        });
    }

    private void carregarMensagens() {
        DatabaseReference mensagensRef = FirebaseDatabase.getInstance().getReference("messages");

        mensagensRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensagens.clear(); // Limpa a lista antes de adicionar as mensagens

                for (DataSnapshot mensagemSnapshot : dataSnapshot.getChildren()) {
                    Mensagem mensagem = mensagemSnapshot.getValue(Mensagem.class);
                    String remetente = mensagem.getRemetente();
                    String destinatario = mensagem.getDestinatario();

                    // Verifica se a mensagem é entre o usuário atual e o destinatário da conversa
                    // Verifica se o remetente é igual ao título da conversa e o usuário atual é o destinatário
                    if (remetente.equals(tituloConversa) && destinatario.equals(usuarioAtual)) {
                        mensagens.add(mensagem);
                    }

                   // Verifica se o remetente é igual ao usuário atual e o título da conversa é o destinatário
                    if (remetente.equals(usuarioAtual) && destinatario.equals(tituloConversa)) {
                        mensagens.add(mensagem);
                    }
                }

                MensagemAdapter mensagemAdapter = new MensagemAdapter(mensagens);
                recyclerViewMensagens.setAdapter(mensagemAdapter);
                recyclerViewMensagens.scrollToPosition(mensagens.size() - 1);
                mensagemAdapter.notifyDataSetChanged(); // Notifica o adaptador sobre as mudanças nos dados
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trate o erro caso ocorra uma falha na leitura do banco de dados
            }
        });
    }



    private void adicionarMensagens(DataSnapshot dataSnapshot) {
        for (DataSnapshot mensagemSnapshot : dataSnapshot.getChildren()) {
            Mensagem mensagem = mensagemSnapshot.getValue(Mensagem.class);
            mensagens.add(mensagem);
        }

        MensagemAdapter mensagemAdapter = new MensagemAdapter(mensagens);
        recyclerViewMensagens.setAdapter(mensagemAdapter);
        recyclerViewMensagens.scrollToPosition(mensagens.size() - 1);
        mensagemAdapter.notifyDataSetChanged(); // Notifica o adaptador sobre as mudanças nos dados
    }










    private void enviarMensagem() {
        String conteudo = editTextMensagem.getText().toString().trim();
        if (!conteudo.isEmpty()) {
            String dataHora = getCurrentDateTime();
            String remetente = usuarioAtual;
            String destinatario = tituloConversa;

            Mensagem mensagem = new Mensagem(conteudo, destinatario, remetente);
            DatabaseReference mensagensRef = FirebaseDatabase.getInstance().getReference("messages");
            mensagensRef.push().setValue(mensagem);

            editTextMensagem.setText("");
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }

    public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.ViewHolder> {
        private List<Mensagem> mensagens;

        public MensagemAdapter(List<Mensagem> mensagens) {
            this.mensagens = mensagens;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Mensagem mensagem = mensagens.get(position);

            holder.textViewRemetente.setText(mensagem.getRemetente());
            holder.textViewMensagem.setText(mensagem.getConteudo());
            holder.textViewDataHora.setText(mensagem.getDataHora());
        }

        @Override
        public int getItemCount() {
            //Verifique se o método getItemCount() no adaptador MensagemAdapter está retornando o número correto de itens na lista mensagens.
            return mensagens.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewRemetente;
            TextView textViewMensagem;
            TextView textViewDataHora;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewRemetente = itemView.findViewById(R.id.textViewRemetente);
                textViewMensagem = itemView.findViewById(R.id.textViewMensagem);
                textViewDataHora = itemView.findViewById(R.id.textViewDataHora);
            }
        }
    }}