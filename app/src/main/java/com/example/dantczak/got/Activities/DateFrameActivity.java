package com.example.dantczak.got.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.TinyDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateFrameActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;
    private CheckBox ignoreStartDate;
    private CheckBox ignoreEndDate;
    private TinyDb tinyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        tinyDb = new TinyDb(this);
        findViews();
        setupButtonListeners();
        setupDatePickers();
        setupCheckboxes();
    }

    private void setupCheckboxes()
    {
        ignoreStartDate = findViewById(R.id.ignore_start_date);
        ignoreEndDate = findViewById(R.id.ignore_end_date);

        if(tinyDb.getString(getResources().getString(R.string.ranking_start_date)).isEmpty())
        {
            ignoreStartDate.setChecked(true);
            setTextColorAndClickable(startDate, ignoreStartDate.isChecked());
        }
        if(tinyDb.getString(getResources().getString(R.string.ranking_end_date)).isEmpty())
        {
            ignoreEndDate.setChecked(true);
            setTextColorAndClickable(endDate, ignoreEndDate.isChecked());
        }

        ignoreStartDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTextColorAndClickable(startDate, isChecked);
            }
        });

        ignoreEndDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTextColorAndClickable(endDate, isChecked);
            }
        });
    }

    private void setTextColorAndClickable(TextView textView, boolean isFaded)
    {
        textView.setTextColor(isFaded ?
                getResources().getColor(R.color.colorDividerColor) :
                getResources().getColor(R.color.colorSecondaryText));

        textView.setClickable(!isFaded);
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
                String startDateString = ignoreStartDate.isChecked() ? "" : startDate.getText().toString();
                tinyDb.putString(getResources().getString(R.string.ranking_start_date), startDateString);

                String endDateString = ignoreEndDate.isChecked() ? "" : endDate.getText().toString();
                tinyDb.putString(getResources().getString(R.string.ranking_end_date), endDateString);

                finish();
            }
        });
    }

    private void setupDatePickers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
        String sds = tinyDb.getString(getResources().getString(R.string.ranking_start_date));
        String eds = tinyDb.getString(getResources().getString(R.string.ranking_end_date));

        Calendar startCal = new GregorianCalendar();
        Calendar endCal = new GregorianCalendar();


        if(sds.isEmpty())
        {
            Calendar c = new GregorianCalendar();
            c.set(2000, 0, 1);
            startCal.setTime(c.getTime());
        }
        else
        {
            try { startCal.setTime(sdf.parse(sds)); }
            catch (Exception e) { e.printStackTrace(); }
        }

        if(eds.isEmpty())
        {
            endCal.setTime(Calendar.getInstance().getTime());
        }
        else
        {
            try { endCal.setTime(sdf.parse(eds)); }
            catch (Exception e) { e.printStackTrace(); }
        }

        startDate.setText(sdf.format(startCal.getTime()));
        endDate.setText(sdf.format(endCal.getTime()));
        startDateDialog = new DatePickerDialog(this, this, startCal.get(Calendar.YEAR),
                startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH));
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


        endDateDialog = new DatePickerDialog(this, this, endCal.get(Calendar.YEAR),
                endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH));
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
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, dayOfMonth);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
