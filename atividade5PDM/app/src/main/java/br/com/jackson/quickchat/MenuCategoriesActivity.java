package br.com.jackson.quickchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categories);
    }

    public void goToCategoryGeneral(View view){

        String category = getString(R.string.general_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "General");

    }

    public void goToCategoryCinema(View view){

        String category = getString(R.string.cinema_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "Cinema");

    }


    public void goToCategoryNews(View view){

        String category = getString(R.string.news_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "News");

    }
    public void goToCategoryTechnology(View view){

        String category = getString(R.string.technology_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "Technology");

    }
    public void goToCategoryEconomy(View view){

        String category = getString(R.string.economy_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "Economy");

    }
    public void goToCategorySports(View view){
        String category = getString(R.string.sports_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "Sports");
    }
    public void goToCategoryLanguages(View view){
        String category = getString(R.string.language_category);
        ArrayList<String> opcoes = new ArrayList<>();
        setIntentAndExtras(opcoes, category, "Languages");
    }

    public void setIntentAndExtras(ArrayList<String> opcoes, String category, String option){
        Intent intent = new Intent(MenuCategoriesActivity.this, ChatActivity.class);

        opcoes.add(category);
        opcoes.add(option);
        intent.putExtra("category_option", opcoes);

        startActivity(intent);
    }
}
