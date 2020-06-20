package br.com.jackson.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameEditProfileEditText;
    private EditText emailEditProfileEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEditProfileEditText = findViewById(R.id.nameEditProfileEditText);
        emailEditProfileEditText = findViewById(R.id.emailEditProfileEditText);
    }

    public void confirmEditProfile(View view){
        final String email = emailEditProfileEditText.getEditableText().toString();
        final String name = nameEditProfileEditText.getEditableText().toString();

        if(validateFields(name, email)){

            if(validateEmail(email)){



                    final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                    final String userId = fbuser.getUid();

//                    User user = new User(name, email);
//
//                    refUser.child("users").child(userId).setValue(user);

                    fbuser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                User user = new User(name, email);
                                refUser.child("users").child(userId).setValue(user);

                            }else{

                            }
                        }
                    });

                    Toast.makeText(EditProfileActivity.this, getString(R.string.userdata_update_message),
                            Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(EditProfileActivity.this, getString(R.string.invalid_email_format_warning),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(EditProfileActivity.this, getString(R.string.empty_field_warning),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validateFields(String name, String login){
        return !login.isEmpty() && !name.isEmpty();
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
