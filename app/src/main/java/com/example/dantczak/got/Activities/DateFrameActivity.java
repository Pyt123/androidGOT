package com.example.dantczak.got.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.TinyDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateFrameActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        findViews();
        setupButtonListeners();
        setupDatePickers();
    }

    private void setupView() {
        setContentView(R.layout.activity_date_frame);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findViews()
    {
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
    }

    private void setupButtonListeners()
    {
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDialog.show();
                startDateDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                startDateDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog.show();
                endDateDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                endDateDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDb tinyDb = new TinyDb(getApplicationContext());
                tinyDb.putString(getResources().getString(R.string.ranking_start_date), startDate.getText().toString());
                tinyDb.putString(getResources().getString(R.string.ranking_end_date), endDate.getText().toString());
                finish();
            }
        });
    }

    private void setupDatePickers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
        TinyDb tinyDb = new TinyDb(getApplicationContext());
        String sds = tinyDb.getString(getResources().getString(R.string.ranking_start_date));
        String eds = tinyDb.getString(getResources().getString(R.string.ranking_end_date));
        Date sd, ed;
        try
        {
            sd = sdf.parse(sds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sd = new Date(0);
        }
        try
        {
            ed = sdf.parse(eds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ed = Calendar.getInstance().getTime();
        }

        startDate.setText(sdf.format(sd));
        endDate.setText(sdf.format(ed));

        startDateDialog = new DatePickerDialog(this, this, sd.getYear() + 1900, sd.getMonth(), sd.getDay());
        startDateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String date = createDateString(startDateDialog.getDatePicker().getDayOfMonth(),
                        startDateDialog.getDatePicker().getMonth(), startDateDialog.getDatePicker().getYear());
                startDate.setText(date);
                startDateDialog.dismiss();
            }
        });
        startDateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startDateDialog.dismiss();
            }
        });

        endDateDialog = new DatePickerDialog(this, this, ed.getYear() + 1900, ed.getMonth(), ed.getDay());
        endDateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String date = createDateString(endDateDialog.getDatePicker().getDayOfMonth(),
                        endDateDialog.getDatePicker().getMonth(), endDateDialog.getDatePicker().getYear());
                endDate.setText(date);
                endDateDialog.dismiss();
            }
        });
        endDateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                endDateDialog.dismiss();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }

    private String createDateString(int dayOfMonth, int month, int year)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
        Date date = new Date(year-1900, month, dayOfMonth);
        return simpleDateFormat.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
