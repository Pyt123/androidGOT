package com.example.dantczak.got.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.TinyDb;
import com.example.dantczak.got.model.uzytkownik.Turysta;
import com.fasterxml.jackson.databind.JavaType;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RankingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRanking();
    }

    private void setupRanking() {
        try
        {
            TinyDb tinyDb = new TinyDb(this);
            String sd = tinyDb.getString(getResources().getString(R.string.ranking_start_date));
            if(sd.isEmpty())
            {
                sd = "01-01-1972";
            }
            String ed = tinyDb.getString(getResources().getString(R.string.ranking_end_date));
            if(ed.isEmpty())
            {
                ed = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).format(Calendar.getInstance().getTime());
            }
            List<Long> groups = tinyDb.getListLong(getResources().getString(R.string.ranking_mountain_group_ids));

            HttpUtils.getWithBody(getApplicationContext(), "ranking/wyswietl/" + sd + "/" + ed, groups,
                    new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.v("request failure", responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try
                            {
                                JavaType jt = JsonUtils.getGenericListType("com.example.dantczak.got.model.uzytkownik.Turysta");
                                List<Turysta> result = JsonUtils.getObjectMapper().readValue(responseString, jt);
                                setupRankingList(result);
                            }
                            catch (Exception e) { e.printStackTrace(); }
                        }
                    });
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setupRankingList(List<Turysta> rankList)
    {
        RecyclerView recyclerView = findViewById(R.id.ranking_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RankingAdapter adapter = new RankingAdapter(rankList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupView() {
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupButtonListeners()
    {
        View view = findViewById(R.id.grupy_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MountainGroupActivity.class);
                startActivity(intent);
            }
        });

        view = findViewById(R.id.daty_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DateFrameActivity.class);
                startActivity(intent);
            }
        });
    }
}

class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder>
{
    private List<Turysta> touristList;
    private Context context;

    public RankingAdapter(List<Turysta> touristList, Context context)
    {
        this.setTouristList(touristList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        Turysta tourist = touristList.get(position);
        viewHolder.name.setText(tourist.getImie() + " " + tourist.getNazwisko().substring(0,1) + ".");
        viewHolder.points.setText(tourist.getZgromadzonePunkty().toString());
    }

    @Override
    public int getItemCount()
    {
        if(touristList == null)
        {
            return 0;
        }
        else
        {
            return touristList.size();
        }
    }

    public void setTouristList(List<Turysta> touristList) {
        this.touristList = touristList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView points;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name_text);
            points = itemView.findViewById(R.id.points_text);
        }


        public TextView getName() {
            return name;
        }

        public TextView getPoints() {
            return points;
        }
    }
}
