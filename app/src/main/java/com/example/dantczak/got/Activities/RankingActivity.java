package com.example.dantczak.got.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dantczak.got.DTO.RankList;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.RecycleViewDividerDecorator;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;
import com.example.dantczak.got.Utils.StaticValues;
import com.example.dantczak.got.Utils.TinyDb;
import com.fasterxml.jackson.databind.JavaType;

import org.w3c.dom.Text;

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

            String rankHeadingString = "Ranking";
            if(sd.isEmpty())
            {
                sd = "01-01-2000";
            }
            else
            {
                rankHeadingString += String.format(" od %s", sd);
            }
            String ed = tinyDb.getString(getResources().getString(R.string.ranking_end_date));
            if(ed.isEmpty())
            {
                ed = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).format(Calendar.getInstance().getTime());
            }
            else
            {
                rankHeadingString += String.format(" do %s", ed);
            }
            TextView rankHeadingText = findViewById(R.id.rank_heading);
            rankHeadingText.setText(rankHeadingString);

            List<Long> groups = tinyDb.getListLong(getResources().getString(R.string.ranking_mountain_group_ids));

            HttpUtils.getWithBody(getApplicationContext(), "ranking/wyswietl/",
                    groups,
                    new OnlySuccessMattersHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try
                            {
                                JavaType jt = JsonUtils.getObjectType("com.example.dantczak.got.DTO.RankList");
                                RankList result = JsonUtils.getObjectMapper().readValue(responseString, jt);
                                setupRankingList(result);
                            }
                            catch (Exception e) { e.printStackTrace(); }
                        }
                    },
                    StaticValues.loggedInTurystaId.toString(), sd, ed);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setupRankingList(RankList rankList)
    {
        RecyclerView recyclerView = findViewById(R.id.ranking_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RankingAdapter adapter = new RankingAdapter(rankList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDividerDecorator(this));
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
    private RankList rankList;
    private Context context;

    public RankingAdapter(RankList rankList, Context context)
    {
        this.rankList = rankList;
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
        if(position == rankList.getReqTouristIndex())
        {
            viewHolder.getRanking_row_ll().setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
        }
        else
        {
            viewHolder.getRanking_row_ll().setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
        }
        String name = rankList.getEntries().get(position).getFirst();
        viewHolder.name.setText(name);
        Integer points = rankList.getEntries().get(position).getSecond();
        viewHolder.points.setText(points.toString());
    }

    @Override
    public int getItemCount()
    {
        if(rankList == null)
        {
            return 0;
        }
        else
        {
            return rankList.getEntries().size();
        }
    }

    public RankList getRankList() {
        return rankList;
    }

    public void setRankList(RankList rankList) {
        this.rankList = rankList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView points;
        private LinearLayout ranking_row_ll;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name_text);
            points = itemView.findViewById(R.id.points_text);
            ranking_row_ll = itemView.findViewById(R.id.ranking_row_ll);
        }


        public TextView getName() {
            return name;
        }

        public TextView getPoints() {
            return points;
        }

        public LinearLayout getRanking_row_ll() {
            return ranking_row_ll;
        }
    }
}
