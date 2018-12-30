package com.example.dantczak.got;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

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
                endDateDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
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
    }

    private void setupDatePickers() {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        startDateDialog = new DatePickerDialog(this, this, yy, mm, dd);
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

        endDateDialog = new DatePickerDialog(this, this, yy, mm, dd);
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
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.GERMANY);
        Date date = new Date(year-1900, month, dayOfMonth);
        return simpleDateFormat.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
