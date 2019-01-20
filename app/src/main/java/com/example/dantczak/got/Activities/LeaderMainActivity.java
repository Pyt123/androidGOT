package com.example.dantczak.got.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dantczak.got.DTO.PathToVerify;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;
import com.fasterxml.jackson.databind.JavaType;

import cz.msebera.android.httpclient.Header;

import com.example.dantczak.got.Utils.StaticMockValues;

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
                getEntryToVerification();
            }
        });
    }

    private void getEntryToVerification()
    {
        final ProgressDialog pd = ProgressDialog.show(LeaderMainActivity.this, "", "Pobieranie danych...", true);
        final Toast failToast = Toast.makeText(this, "Wystapił błąd... Proszę spróbować później.", Toast.LENGTH_LONG);
        try
        {
            HttpUtils.get("weryfikacja/znajdz_trase/",
                    new OnlySuccessMattersHandler(pd, failToast) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try
                            {
                                super.onSuccess();
                                if(responseString.isEmpty())
                                {
                                    informNoEntries();
                                }
                                else
                                {
                                    JavaType jt = JsonUtils.getObjectType("com.example.dantczak.got.DTO.PathToVerify");
                                    PathToVerify result = JsonUtils.getObjectMapper().readValue(responseString, jt);
                                    verifyEntry(result);
                                }
                            }
                            catch (Exception e) { e.printStackTrace(); }
                        }
                    }, StaticMockValues.loggedInLeaderId.toString());
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void informNoEntries()
    {
        dialog.show();
    }

    private void verifyEntry(@NonNull PathToVerify pathToVerify)
    {
        Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
        // put extra nie wchodzi w grę, ponieważ rozmiar przekracza 1MB
        VerificationActivity.setPathToVerifyInstance(pathToVerify);
        startActivityForResult(intent, 0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && data.getBooleanExtra(getResources().getString(R.string.next_verification), false))
        {
            getEntryToVerification();
        }
    }
}
