package devandroid.zocarato.testefirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConversaDAO {

    public interface ConversaListener {
        void onConversasLoaded(List<ConversaModel> conversas);
        void onCancelled(DatabaseError error);
    }

    public static void getConversas(final ConversaListener listener) {
        DatabaseReference conversasRef = FirebaseDatabase.getInstance().getReference("messages");

        conversasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ConversaModel> conversas = new ArrayList<>();

                for (DataSnapshot conversaSnapshot : dataSnapshot.getChildren()) {
                    String destinatario = conversaSnapshot.child("destinatario").getValue(String.class);
                    String remetente = conversaSnapshot.child("remetente").getValue(String.class);
                    String ultimaMensagem = conversaSnapshot.child("ultimaMensagem").getValue(String.class);
                    String dataHoraUltimaMensagem = conversaSnapshot.child("dataHoraUltimaMensagem").getValue(String.class);
                    String conteudo = conversaSnapshot.child("conteudo").getValue(String.class);

                    ConversaModel conversa = new ConversaModel( destinatario,  remetente,  ultimaMensagem,  dataHoraUltimaMensagem);
                    conversas.add(conversa);
                }

                listener.onConversasLoaded(conversas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }
}

