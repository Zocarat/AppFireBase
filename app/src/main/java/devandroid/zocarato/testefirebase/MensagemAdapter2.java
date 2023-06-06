package devandroid.zocarato.testefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class MensagemAdapter2 extends RecyclerView.Adapter<MensagemAdapter2.ViewHolder> {
    private List<Mensagem> mensagens;

    public MensagemAdapter2(List<Mensagem> mensagens) {
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
}