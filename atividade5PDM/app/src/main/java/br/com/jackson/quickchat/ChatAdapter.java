package br.com.jackson.quickchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter <ChatViewHolder> {

    private List<Message> messages;
    private Context context;


    public ChatAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.list_item, parent, false);
        return new ChatViewHolder(raiz);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message m = messages.get(position);
        holder.getMensagemTextView().setText(m.getText());
        holder.getDataNomeTextView().setText(
                context.getString(R.string.date_name,
                        DateHelper.format(m.getDate()),
                        m.getUser())
        );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
