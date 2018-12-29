package com.example.dantczak.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class LeaderMainActivity extends AppCompatActivity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupButtonListeners();
        setupDialog();
    }

    private void setupDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        builder.setMessage("Brak oczekujących zgłoszeń, do których rozpatrywania masz uprawnienia.")
                .setTitle("Brak zgłoszeń")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
    }

    private void setupView() {
        setContentView(R.layout.activity_leader_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupButtonListeners() {
        Button button = findViewById(R.id.zweryfikuj_trase_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEntryWaiting())
                {
                    Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                    startActivity(intent);
                }
                else
                {
                    dialog.show();
                }
            }
        });
    }

    private boolean isEntryWaiting()
    {
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.leader_panel_action) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
