package com.example.dantczak.got.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dantczak.got.DTO.Status;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.StaticMockValues;
import com.example.dantczak.got.Utils.TinyDb;

public class MainActivity extends AppCompatActivity{

    private AlertDialog ipDialog;
    private TinyDb tinyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupButtonListeners();
        setupStaticMockUrl();
    }

    private void setupStaticMockUrl() {
        tinyDb = new TinyDb(this);
        String ip = tinyDb.getString(getResources().getString(R.string.ip_key));
        StaticMockValues.BASE_URL = "http://" + ip;
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupButtonListeners() {
        Button button = findViewById(R.id.ranking_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.uloz_wedrowke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetupWalkActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.leader_panel_action)
        {
            Intent intent = new Intent(this, LeaderMainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.change_ip_action)
        {
            CreateIpDialog().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private AlertDialog CreateIpDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setMessage("Ustaw IP oraz port:")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try
                {
                    TextView textView = ipDialog.findViewById(android.R.id.text1);
                    String ip = textView.getText().toString();
                    tinyDb.putString(getResources().getString(R.string.ip_key), ip);
                    StaticMockValues.BASE_URL = "http://" + ip;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
        });
        final EditText input = new EditText(this);
        input.setText(tinyDb.getString(getResources().getString(R.string.ip_key)));
        input.setId(android.R.id.text1);
        builder.setView(input);

        ipDialog = builder.create();
        return ipDialog;
    }
}
