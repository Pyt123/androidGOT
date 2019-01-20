package com.example.dantczak.got.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dantczak.got.DTO.Pair;
import com.example.dantczak.got.DTO.PathToVerify;
import com.example.dantczak.got.DTO.Status;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.RecycleViewDividerDecorator;
import com.example.dantczak.got.Utils.ResponseHandlers.NoReponseHandler;
import com.example.dantczak.got.Utils.StaticValues;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class VerificationActivity extends AppCompatActivity
{
    private static PathToVerify pathToVerify;
    public static PathToVerify getPathToVerifyInstance() { return pathToVerify; }
    public static void setPathToVerifyInstance(PathToVerify pth) { pathToVerify = pth; }

    private AlertDialog pointsDialog;
    private AlertDialog nextVerificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setupDialogs();
        setupButtonListeners();
        setupViewsWithPathValues();
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
        PathPointsAdapter adapter = new PathPointsAdapter(pathToVerify.getPointNamesAndCords(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDividerDecorator(this));
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
                if(pathToVerify.getCanModifyRankPoints())
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
                startActivity(intent);
            }
        });
    }

    private void setStatusAndContinue(Status status)
    {
        HttpUtils.post("weryfikacja/ustaw_status/",
                new NoReponseHandler(),
                pathToVerify.getVerifyPathId().toString(), status.toString(),
                StaticValues.loggedInPrzodownikId.toString(), pathToVerify.getRankPointsFor().toString());

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

        if(pathToVerify.getCanModifyRankPoints())
        {
            builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setMessage("Przyznane punkty: " + pathToVerify.getRankPointsFor())
                    .setTitle("Trasa niepunktowana")
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try
                    {
                        TextView textView = pointsDialog.findViewById(android.R.id.text1);
                        String pointsString = textView.getText().toString();
                        int points = Integer.parseInt(pointsString);
                        pathToVerify.setRankPointsFor(points);
                    }
                    catch (Exception e) { e.printStackTrace(); }
                    setStatusAndContinue(Status.potwierdzona);
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
}

class PathPointsAdapter extends RecyclerView.Adapter<PathPointsAdapter.ViewHolder>
{
    private List<Pair<String, String>> pointNamesAndCords;
    private Context context;

    public PathPointsAdapter(List<Pair<String, String>> pointNamesAndCords, Context context)
    {
        this.pointNamesAndCords = pointNamesAndCords;
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
        viewHolder.name.setText(pointNamesAndCords.get(position).getFirst());
        viewHolder.coords.setText(pointNamesAndCords.get(position).getSecond());
    }

    @Override
    public int getItemCount()
    {
        return pointNamesAndCords.size();
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
