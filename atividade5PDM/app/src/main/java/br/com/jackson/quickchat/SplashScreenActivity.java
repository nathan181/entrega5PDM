package br.com.jackson.quickchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginScreen();
            }
        }, 5000); //wait x secs to change screen

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashScreenActivity.this, getString(R.string.programmer_information), Toast.LENGTH_LONG).show();
            }
        }, 1500);
    }

    private void showLoginScreen(){
        //show Login Screen and finish this one
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}