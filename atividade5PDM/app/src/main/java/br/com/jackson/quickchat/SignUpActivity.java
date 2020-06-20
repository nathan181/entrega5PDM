package br.com.jackson.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference refUser;

    private EditText nameSignUpEditText;
    private EditText emailSignUpEditText;
    private EditText passwordSignUpEditText;
    private EditText passwordSignUpEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameSignUpEditText = findViewById(R.id.nameSignUpEditText);
        emailSignUpEditText = findViewById(R.id.emailSignUpEditText);
        passwordSignUpEditText = findViewById(R.id.passwordSignUpEditText);
        passwordSignUpEditText2 = findViewById(R.id.passwordSignUpEditText2);

        mAuth = FirebaseAuth.getInstance();
        refUser = FirebaseDatabase.getInstance().getReference().child("users");

    }



    public void confirmRegistration(View view){

        final String email = emailSignUpEditText.getEditableText().toString();
        final String password = passwordSignUpEditText.getEditableText().toString();
        final String name = nameSignUpEditText.getEditableText().toString();
        String password2 = passwordSignUpEditText2.getEditableText().toString();

        if(validateFields(email, password, password2, name)){

            if(validateMinLengthPassword(password, password2)){

                if(validateEqualPasswords(password, password2)){

                    mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                writeNewUser(userId, name, email);
                                goToLoginActivity();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, getString(R.string.registration_fail),
                                    Toast.LENGTH_SHORT).show();
                            goToLoginActivity();
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, getString(R.string.different_passwords_warning),
                            Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(SignUpActivity.this, getString(R.string.min_length_passwords_warning),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(SignUpActivity.this, getString(R.string.empty_field_warning),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void goToMenuCategoriesActivity(){
        Intent intent = new Intent(SignUpActivity.this, MenuCategoriesActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToLoginActivity(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public boolean validateFields(String login, String password1, String password2, String name){
        return !login.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && !name.isEmpty();
    }

    public boolean validateEqualPasswords(String password1, String password2){
        return password1.equals(password2);
    }
    public boolean validateMinLengthPassword(String password1, String password2){
        return password1.length() > 7 || password2.length() > 7;
    }

    private void writeNewUser(String userId, String name, String email){
        //usando o mesmo UID do Firebase Authentication: userId

        try {//tentando cadastrar no banco
            User user = new User(name, email);
            refUser.child(userId).setValue(user);
            Toast.makeText(SignUpActivity.this, getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT).show();

        }
        catch(DatabaseException e){
            e.printStackTrace();
            if(mAuth.getCurrentUser() != null){
                mAuth.getCurrentUser().delete();
            }
            Toast.makeText(SignUpActivity.this, getString(R.string.registration_fail_userdata),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void testeToast(int vez){
        Toast.makeText(SignUpActivity.this, "passei aqui: " + vez,
                Toast.LENGTH_SHORT).show();
    }
}
