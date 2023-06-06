package devandroid.zocarato.testefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversasActivity extends AppCompatActivity {
    private RecyclerView recyclerViewConversas;
    private ConversaAdapter conversaAdapter;
    private List<Conversa> conversas;
    private String usuarioAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);
        usuarioAtual = Global.getInstance().getLogin();

        recyclerViewConversas = findViewById(R.id.recyclerViewConversas);
        recyclerViewConversas.setLayoutManager(new LinearLayoutManager(this));

        conversas = new ArrayList<>();

        conversaAdapter = new ConversaAdapter(conversas);
        recyclerViewConversas.setAdapter(conversaAdapter);

        recyclerViewConversas.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    int position = rv.getChildAdapterPosition(childView);
                    abrirDetalhesConversa(conversas.get(position), position);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Não é necessário implementar nada aqui
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                // Não é necessário implementar nada aqui
            }
        });

        getConversasFromDatabase(); // Recupere os dados das conversas do banco de dados
    }

    private void getConversasFromDatabase() {
        DatabaseReference mensagensRef = FirebaseDatabase.getInstance().getReference("messages");
        Query conversasQuery = mensagensRef.orderByChild("timestamp");

        conversasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                conversas.clear(); // Limpa a lista antes de adicionar as conversas

                for (DataSnapshot conversaSnapshot : dataSnapshot.getChildren()) {
                    Conversa conversa = conversaSnapshot.getValue(Conversa.class);

                    // Verifique se o usuário atual está envolvido na conversa
                    if (conversa != null && (usuarioAtual.equals(conversa.getRemetente()) ||
                            usuarioAtual.equals(conversa.getDestinatario()))) {
                        // Verifique se a conversa já existe na lista
                        boolean conversaExistente = false;
                        for (Conversa conv : conversas) {
                            if ((conv.getDestinatario().equals(conversa.getDestinatario()) &&
                                    conv.getRemetente().equals(conversa.getRemetente())) ||
                                    (conv.getDestinatario().equals(conversa.getRemetente()) &&
                                            conv.getRemetente().equals(conversa.getDestinatario()))) {
                                conversaExistente = true;
                                // Atualize a conversa existente com os novos dados
                                conv.setUltimaMensagem(conversa.getUltimaMensagem());
                                conv.setDataHoraUltimaMensagem(conversa.getDataHoraUltimaMensagem());
                                break;
                            }
                        }

                        // Se a conversa não existe na lista, adicione-a
                        if (!conversaExistente) {
                            conversas.add(conversa);
                        }
                    }
                }

                conversaAdapter.notifyDataSetChanged(); // Notifica o adapter sobre as mudanças nos dados
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trate o erro caso ocorra uma falha na leitura do banco de dados
                // Aqui você pode exibir uma mensagem de erro ou realizar outras ações de tratamento
            }
        });
    }


    private void abrirDetalhesConversa(Conversa conversa, int position) {
        Intent intent = new Intent(ConversasActivity.this, DetalhesConversaActivity.class);
        intent.putExtra("conversa", conversa);

        String nomeConversa;
        if (conversa.getDestinatario().equals(usuarioAtual)) {
            nomeConversa = conversa.getRemetente();
        } else {
            nomeConversa = conversa.getDestinatario();
        }
        String tituloConversa = nomeConversa;

        intent.putExtra("conversa", conversa);
        intent.putExtra("tituloConversa", tituloConversa);
        startActivity(intent);
    }

    // Adapter interno para exibir as conversas
    private class ConversaAdapter extends RecyclerView.Adapter<ConversaAdapter.ConversaViewHolder> {
        private List<Conversa> conversas;

        public ConversaAdapter(List<Conversa> conversas) {
            this.conversas = conversas;
        }

        @NonNull
        @Override
        public ConversaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversa, parent, false);
            return new ConversaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ConversaViewHolder holder, int position) {
            Conversa conversa = conversas.get(position);
            holder.bind(conversa);
        }

        @Override
        public int getItemCount() {
            return conversas.size();
        }

        // ViewHolder interno para exibir os itens da conversa
        public class ConversaViewHolder extends RecyclerView.ViewHolder {
            private MaterialTextView textViewNomeDestinatario;
            private MaterialTextView textViewUltimaMensagem;
            private MaterialTextView textViewDataHoraUltimaMensagem;

            public ConversaViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewNomeDestinatario = itemView.findViewById(R.id.textViewNomeDestinatario);
                textViewUltimaMensagem = itemView.findViewById(R.id.textViewUltimaMensagem);
                textViewDataHoraUltimaMensagem = itemView.findViewById(R.id.textViewDataHoraUltimaMensagem);
            }

            public void bind(Conversa conversa) {
                String nomeConversa;
                if (conversa.getDestinatario().equals(usuarioAtual)) {
                    nomeConversa = conversa.getRemetente();
                } else {
                    nomeConversa = conversa.getDestinatario();
                }

                textViewNomeDestinatario.setText(nomeConversa);
                textViewUltimaMensagem.setText(conversa.getUltimaMensagem());
                textViewDataHoraUltimaMensagem.setText(conversa.getDataHoraUltimaMensagem());
            }

        }
    }

    public static class Conversa implements Serializable {
        private String destinatario;
        private String remetente;
        private String ultimaMensagem;
        private String dataHoraUltimaMensagem;

        public Conversa() {
            // Construtor vazio necessário para o Firebase
        }

        public Conversa(String destinatario, String remetente, String ultimaMensagem, String dataHoraUltimaMensagem) {
            this.destinatario = destinatario;
            this.remetente = remetente;
            this.ultimaMensagem = ultimaMensagem;
            this.dataHoraUltimaMensagem = dataHoraUltimaMensagem;
        }

        public String getDestinatario() {
            return destinatario;
        }

        public void setDestinatario(String destinatario) {
            this.destinatario = destinatario;
        }

        public String getRemetente() {
            return remetente;
        }

        public void setRemetente(String remetente) {
            this.remetente = remetente;
        }

        public String getUltimaMensagem() {
            return ultimaMensagem;
        }

        public void setUltimaMensagem(String ultimaMensagem) {
            this.ultimaMensagem = ultimaMensagem;
        }

        public String getDataHoraUltimaMensagem() {
            return dataHoraUltimaMensagem;
        }

        public void setDataHoraUltimaMensagem(String dataHoraUltimaMensagem) {
            this.dataHoraUltimaMensagem = dataHoraUltimaMensagem;
        }
    }
}