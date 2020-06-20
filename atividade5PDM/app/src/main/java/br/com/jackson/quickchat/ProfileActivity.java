package br.com.jackson.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference refUser, frefUser;
    private ValueEventListener userListener;

    private TextView nameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        refUser = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public void goToEditProfile(View view){
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);

    }

    public void signOut(View view){
        mAuth.signOut();
        frefUser.removeEventListener(userListener);
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMenuCategories(View view){
        Intent intent = new Intent(ProfileActivity.this, MenuCategoriesActivity.class);
        startActivity(intent);

    }

    public void deleteAccount (View view){
        deleteUserAccount();
        signOut(view);
    }

    public void deleteUserAccount(){
        refUser.child(firebaseUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, getString(R.string.delete_userdata_success), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProfileActivity.this, getString(R.string.delete_userdata_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, getString(R.string.account_delete_success), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProfileActivity.this, getString(R.string.account_delete_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showUserdata(){
        frefUser = refUser.child(firebaseUser.getUid());

        frefUser.addValueEventListener(userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // [START_EXCLUDE]
                if (user != null) {
                    nameTextView.setText(user.name);
                    emailTextView.setText(user.email);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, getString(R.string.userdata_loading_failure),
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        showUserdata();
    }

    @Override
    protected void onStop() {
        super.onStop();
        frefUser.removeEventListener(userListener);
    }
}
