package com.example.dantczak.got.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.dantczak.got.Activities.wedrowkawizard.ChoosePunktPoczatkowyActivity;
import com.example.dantczak.got.R;

public class SetupWalkActivity extends AppCompatActivity {


    private Button trasyPunktowaneButton;
    private Button trasyNiepunktowaneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupButtonListeners();
    }

    private void setupView() {
        setContentView(R.layout.activity_setup_walk);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        trasyPunktowaneButton = findViewById(R.id.trasyPunktowaneButton);
        trasyNiepunktowaneButton = findViewById(R.id.trasyNiepunktowaneButton);

    }
    private void setupButtonListeners()
    {
        trasyPunktowaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choosePunktPoczatkowyActivity = new Intent(SetupWalkActivity.this ,ChoosePunktPoczatkowyActivity.class);
                startActivity(choosePunktPoczatkowyActivity);
            }
        });
    }

}
