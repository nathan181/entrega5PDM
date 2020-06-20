package br.com.jackson.quickchat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String option;
    private RecyclerView messagesRecyclerView;
    private ChatAdapter adapter;
    private List<Message> messages;
    private EditText messageEditText;
    private FirebaseUser firebaseUser;
    private CollectionReference messagesReference;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<String> category_option;

        bundle = getIntent().getExtras();
        category_option = bundle.getStringArrayList("category_option");


        Toast.makeText(ChatActivity.this, getString(R.string.welcome_message_chat) + " " +category_option.get(0),
                Toast.LENGTH_SHORT).show();

        option = category_option.get(1);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messages = new ArrayList<>();
        adapter = new ChatAdapter(messages, this);
        messagesRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messageEditText = findViewById(R.id.messageEditText);

    }

    private void setupFirebase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        messagesReference = FirebaseFirestore.getInstance().collection(option);
        getRemoteMsgs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();
    }

    private void getRemoteMsgs(){
        messagesReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                messages.clear();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                    Message m = doc.toObject(Message.class);
                    messages.add(m);
                }
                Collections.sort(messages);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void sendMessage(View view){
        String text = messageEditText.getText().toString();
        Message m = new Message(firebaseUser.getEmail(), new Date(), text);
        messageEditText.setText("");
        hideKeyboard(view);
        messagesReference.add(m);
    }

    private void hideKeyboard(View view){
        InputMethodManager ims = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ims.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onStop() {
        super.onStop();
        bundle.clear();
    }
}