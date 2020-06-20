package br.com.jackson.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void goToSignUp(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn(View view){
        String login = loginEditText.getEditableText().toString();
        String password = passwordEditText.getEditableText().toString();

        if(validateLoginAndPassword(login, password)){

            firebaseAuth.signInWithEmailAndPassword(login, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_auth_sucess), Toast.LENGTH_SHORT).show();
                    goToProfile();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_auth_failure), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });

        }
        else{
            Toast.makeText(LoginActivity.this, getString(R.string.empty_field_warning), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validateLoginAndPassword(String login, String password){
        return !login.isEmpty() && !password.isEmpty();
    }

    public void goToProfile(){
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        loginEditText.setText("nickstilla@hotmail.com");
//        passwordEditText.setText("estoucansado");
//
//    }

    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            goToProfile();
        }
    }

    public void showView(View view){
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        // Set the message show for the Alert time

        final String userEmail = loginEditText.getEditableText().toString();
        String message = String.format(getString(R.string.reset_password_message), userEmail);
        builder.setMessage(message);

        // Set Alert Title
        builder.setTitle(getString(R.string.reset_password_title_alert));

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                if(validateEmail(userEmail)){
                                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(LoginActivity.this, getString(R.string.email_sent_warning),
                                                        Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(LoginActivity.this, getString(R.string.email_not_sent_warning),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, getString(R.string.invalid_email_format_warning),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public boolean validateEmail(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.trim().matches(emailPattern)){
            return true;
        }
        else{
            return false;
        }

    }
}
