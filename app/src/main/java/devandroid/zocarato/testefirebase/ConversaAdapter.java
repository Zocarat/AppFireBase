package devandroid.zocarato.testefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ConversaAdapter extends RecyclerView.Adapter<ConversaAdapter.ConversaViewHolder> {

    private List<ConversaModel> conversas;

    public ConversaAdapter(List<ConversaModel> conversas) {
        this.conversas = conversas;
    }

    @NonNull
    @Override
    public ConversaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversa, parent, false);
        return new ConversaViewHolder(view);
        //int i = 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversaViewHolder holder, int position) {
        ConversaModel conversa = conversas.get(position);
        holder.bind(conversa);
    }

    @Override
    public int getItemCount() {
        return conversas.size();
    }

    public class ConversaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeDestinatario;
        private TextView textViewUltimaMensagem;
        private TextView textViewDataHoraUltimaMensagem;

        public ConversaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeDestinatario = itemView.findViewById(R.id.textViewNomeDestinatario);
            textViewUltimaMensagem = itemView.findViewById(R.id.textViewUltimaMensagem);
            textViewDataHoraUltimaMensagem = itemView.findViewById(R.id.textViewDataHoraUltimaMensagem);
        }

        public void bind(ConversaModel conversa) {
            String nomeExibido;
            String usuarioAtual = Global.getInstance().getLogin();
            if (conversa.getRemetente().equals(usuarioAtual)) {
                nomeExibido = conversa.getDestinatario();
            } else {
                nomeExibido = conversa.getRemetente();
            }
            //// verifica aqui a segunda chamada
            textViewNomeDestinatario.setText(nomeExibido);
        //    textViewUltimaMensagem.setText(conversa.getUltimaMensagem());
       //     textViewDataHoraUltimaMensagem.setText(conversa.getDataHoraUltimaMensagem());
        }
    }

    private void abrirConversa(ConversaModel conversa) {


        // Implemente o código para abrir a tela de detalhes da conversa
        // Você pode passar a conversa como parâmetro para a nova Activity ou Fragment
        // Aqui você pode abrir uma nova Activity ou substituir o fragment atual, dependendo da sua arquitetura
    }


}

