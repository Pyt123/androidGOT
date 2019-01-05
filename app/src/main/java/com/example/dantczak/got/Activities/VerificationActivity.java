package com.example.dantczak.got.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.StaticValues;
import com.example.dantczak.got.model.DTO.PathToVerify;
import com.example.dantczak.got.model.trasa.SkladowyPunktTrasy;
import com.example.dantczak.got.model.trasa.Status;
import com.fasterxml.jackson.databind.JavaType;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class VerificationActivity extends AppCompatActivity {
    private AlertDialog pointsDialog;
    private AlertDialog nextVerificationDialog;

    private PathToVerify pathToVerify = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPath();
        setupDialogs();
        setupButtonListeners();
        setupViewsWithPathValues();
    }

    private void setupPath()
    {
        try
        {
            String json = getIntent().getExtras().getString(getString(R.string.to_verify_entry_json));
            if(json != null)
            {
                JavaType jt = JsonUtils.getObjectType("com.example.dantczak.got.model.DTO.PathToVerify");
                pathToVerify = JsonUtils.getObjectMapper().readValue(json, jt);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setupViewsWithPathValues()
    {
        TextView textView = findViewById(R.id.first_name_text);
        textView.setText(pathToVerify.getTouristName());
        textView = findViewById(R.id.last_name_text);
        textView.setText(pathToVerify.getTouristSurname());
        textView = findViewById(R.id.date_text);
        textView.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).format(pathToVerify.getWalkDate()));

        RecyclerView recyclerView = findViewById(R.id.points_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PathPointsAdapter adapter = new PathPointsAdapter(pathToVerify.getPahtPoints(),this);
        recyclerView.setAdapter(adapter);
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
                if(!pathToVerify.getCanModifyPoints())
                {
                    pointsDialog.show();
                }
                else
                {
                    setStatusAndContinue(Status.potwierdzona);
                }
            }
        });

        view = findViewById(R.id.odrzuc_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusAndContinue(Status.odrzucona);
            }
        });

        view = findViewById(R.id.do_ponownego_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusAndContinue(Status.doPonownegoRozpatrzenia);

            }
        });

        view = findViewById(R.id.gallery_icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                intent.putExtra(getResources().getString(R.string.to_verify_entry_json),
                        getIntent().getStringExtra(getResources().getString(R.string.to_verify_entry_json)));
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setStatusAndContinue(Status status)
    {
        HttpUtils.get(String.format(Locale.GERMANY, "weryfikacja/ustaw_status/%s/%s/%d/%d",
                pathToVerify.getVerifyPahtId(), status.toString(),
                StaticValues.loggedInPrzodownik.getId(), pathToVerify.getPointsFor()),
                null,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.v("request failure", responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) { }
                });
        nextVerificationDialog.show();
    }

    private void setupDialogs()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
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
                        intent.putExtra(getResources().getString(R.string.next_verification),true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
        });
        nextVerificationDialog = builder.create();

        if(!pathToVerify.getCanModifyPoints())
        {
            builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setMessage("Przyznane punkty: " + pathToVerify.getPointsFor())
                    .setTitle("Trasa niepunktowana")
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setStatusAndContinue(Status.potwierdzona);
                    try
                    {
                        TextView textView = pointsDialog.findViewById(android.R.id.text1);
                        String pointsString = textView.getText().toString();
                        int points = Integer.parseInt(pointsString);
                        pathToVerify.setPointsFor(points);
                    }
                    catch (Exception e) { e.printStackTrace(); }
                }
            });
            final EditText input = new EditText(this);
            input.setId(android.R.id.text1);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            input.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            builder.setView(input);
            pointsDialog = builder.create();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String json = data.getStringExtra(getResources().getString(R.string.to_verify_entry_json));
        if (json != null)
        {
            try
            {
                JavaType jt = JsonUtils.getObjectType("com.example.dantczak.got.model.DTO.PathToVerify");
                pathToVerify = JsonUtils.getObjectMapper().readValue(json, jt);
            }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}

class PathPointsAdapter extends RecyclerView.Adapter<PathPointsAdapter.ViewHolder>
{
    private List<SkladowyPunktTrasy> pointsList;
    private Context context;

    public PathPointsAdapter(List<SkladowyPunktTrasy> pointsList, Context context)
    {
        this.pointsList = pointsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.verification_point, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        SkladowyPunktTrasy point = pointsList.get(position);
        viewHolder.name.setText(point.getPunktTrasy().getNazwaPunktu());
        viewHolder.coords.setText(point.getPunktTrasy().getWysokoscGeograficzna()
                + "   " + point.getPunktTrasy().getSzerokoscGeograficzna());
    }

    @Override
    public int getItemCount()
    {
        return pointsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView coords;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.point_name_text);
            coords = itemView.findViewById(R.id.coordinates_text);
        }
    }
}