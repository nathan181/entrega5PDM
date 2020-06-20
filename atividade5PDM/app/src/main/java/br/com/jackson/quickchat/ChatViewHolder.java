package br.com.jackson.quickchat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private TextView dateNameTextView;
    private TextView messageTextView;

    public ChatViewHolder (View raiz){
        super (raiz);
        this.dateNameTextView = raiz.findViewById(R.id.dateNameTextView);
        this.messageTextView = raiz.findViewById(R.id.messageTextView);
    }

    public TextView getDataNomeTextView() {
        return dateNameTextView;
    }

    public void setDataNomeTextView(TextView dataNomeTextView) {
        this.dateNameTextView = dataNomeTextView;
    }

    public TextView getMensagemTextView() {
        return messageTextView;
    }

    public void setMensagemTextView(TextView mensagemTextView) {
        this.messageTextView = mensagemTextView;
    }
}
