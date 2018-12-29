package com.example.dantczak.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class VerificationActivity extends AppCompatActivity {
    private AlertDialog pointsDialog;
    private AlertDialog nextVerificationDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupDialogs();
        setupButtonListeners();
    }

    private void setupView() {
        setContentView(R.layout.activity_verification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setupButtonListeners() {
        View view = findViewById(R.id.zatwierdz_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasScore())
                {
                    pointsDialog.show();
                }
            }
        });

        view = findViewById(R.id.gallery_icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean hasScore()
    {
        return false;
    }

    private void setupDialogs() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        builder.setMessage("Przyznane punkty:")
                .setTitle("Trasa niepunktowana")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //zapisz punkty i tak dalej
                        nextVerificationDialog.show();
                    }
        });
        final EditText input = new EditText(this);
        builder.setView(input);
        pointsDialog = builder.create();


        builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setMessage("Czy chcesz rozpatrzyć kolejne zgłoszenie?")
                .setTitle("Kolejne zgłoszenie")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                     public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
        });
        nextVerificationDialog = builder.create();
    }
}
