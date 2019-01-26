package com.example.dantczak.got.Activities.wedrowkawizard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dantczak.got.DTO.wedrowkawizard.TrasaPunktowanaLite;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ChooseOdcinekTrasyActivity extends AppCompatActivity {
    private static String FETCH_TRASY_URL = "/lite/trasa/punktowana/poczatekwpunkcie/";

    private SearchView searchView;
    private RecyclerView trasyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();

        Bundle bundle = getIntent().getExtras();
        long punktId= bundle.getLong(ChoosePunktPoczatkowyActivity.INTENT_PUNKT_ID);

        fetchTrasyStartingInPoint(punktId);

    }

    private void setupView() {
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_setup_walk_choose_odcinek_trasy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);

        setupRecyclerView();

    }

    private void setupRecyclerView()
    {
        trasyRecyclerView = findViewById(R.id.trasyPktLitesRecyclerView);
        trasyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(trasyRecyclerView.getContext(), DividerItemDecoration.VERTICAL );
        trasyRecyclerView.addItemDecoration(dividerItemDecoration);
    }



    private void populateTrasyViewWithData(List<TrasaPunktowanaLite> trasy) {

        if (trasyRecyclerView.getAdapter()==null) {
            TrasaAdapter adapter = new TrasaAdapter(trasy, getApplicationContext(), new TrasaAdapter.ClickListener() {
                @Override
                public void onItemClick(TrasaPunktowanaLite trasaPunktowanaLite) {
                    trasaClicked(trasaPunktowanaLite);
                }
            });
            trasyRecyclerView.setAdapter(adapter);
        } else
        {
            TrasaAdapter trasaAdapter = (TrasaAdapter) trasyRecyclerView.getAdapter();
            trasaAdapter.setTrasyPunktowane(trasy);
            trasaAdapter.notifyDataSetChanged();

        }
    }

    private void trasaClicked(TrasaPunktowanaLite trasaPunktowanaLite)
    {
        CreateWedrowkaSessionHolder.getInstance().addTrasa(trasaPunktowanaLite);
        startActivity(new Intent(getApplicationContext(), WedrowkaActivity.class));
    }





    private void fetchTrasyStartingInPoint(Long punktId)
    {
        HttpUtils.get(FETCH_TRASY_URL + punktId, new OnlySuccessMattersHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    List<TrasaPunktowanaLite> trasy = JsonUtils.getObjectMapper().readValue(responseString, JsonUtils.getListType(TrasaPunktowanaLite.class));
                    populateTrasyViewWithData(trasy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }




    static class TrasaAdapter extends RecyclerView.Adapter<TrasaAdapter.ViewHolder>
    {
        private List<TrasaPunktowanaLite> trasyPunktowane;
        private Context context;
        private ClickListener clickListener;

        public TrasaAdapter(List<TrasaPunktowanaLite> trasyPunktowane, Context context, ClickListener clickListener)
        {
            this.trasyPunktowane = trasyPunktowane;
            this.context = context;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trasy_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
        {

            String nazwaTrasy= trasyPunktowane.get(position).getNazwaTrasy();
            viewHolder.nazwaTrasy.setText(nazwaTrasy);
            int liczbaPunktow = trasyPunktowane.get(position).getLiczbaPunktow();
            viewHolder.ptsCount.setText("Pkt: "+String.valueOf(liczbaPunktow));
            String distance = String.format("%.2f", trasyPunktowane.get(position).getDistance());
            viewHolder.distance.setText("Dystans: "+distance);
            viewHolder.pktPoczatkowy.setText("Punkt początkowy: "+trasyPunktowane.get(position).getPunktPoczatkowy().getNazwaPunktu());
            viewHolder.pktKoncowy.setText("Punkt końcowy: "+trasyPunktowane.get(position).getPunktKoncowy().getNazwaPunktu());
            viewHolder.setOnClickListener(clickListener);
        }

        @Override
        public int getItemCount()
        {
            if(trasyPunktowane == null)
            {
                return 0;
            }
            else
            {
                return trasyPunktowane.size();
            }
        }

        public List<TrasaPunktowanaLite> getTrasyPunktowane() {
            return trasyPunktowane;
        }

        public void setTrasyPunktowane(List<TrasaPunktowanaLite> trasyPunktowane) {
            this.trasyPunktowane = trasyPunktowane;
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements  RecyclerView.OnClickListener
        {
            private TextView nazwaTrasy;
            private TextView ptsCount;
            private TextView distance;
            private TextView pktPoczatkowy;
            private TextView pktKoncowy;
            private LinearLayout trasy_row_ll;
            private ClickListener clickListener;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                nazwaTrasy = itemView.findViewById(R.id.nazwa_trasy_text);
                distance = itemView.findViewById(R.id.dystans_text);
                ptsCount = itemView.findViewById(R.id.liczba_punktow_text);
                pktPoczatkowy = itemView.findViewById(R.id.nazwa_punkt_poczatkowy_text);
                pktKoncowy = itemView.findViewById(R.id.nazwa_punkt_koncowy_text);
                trasy_row_ll = itemView.findViewById(R.id.trasy_row_ll);

                itemView.setOnClickListener(this);
            }

            public TextView getNazwaTrasy() {
                return nazwaTrasy;
            }

            public TextView getPtsCount() {
                return ptsCount;
            }

            public TextView getDistance() {
                return distance;
            }

            public LinearLayout getTrasy_row_ll() {
                return trasy_row_ll;
            }

            @Override
            public void onClick(View v) {
                TrasaPunktowanaLite trasaPunktowanaLite = getTrasyPunktowane().get(getAdapterPosition());
                clickListener.onItemClick(trasaPunktowanaLite);
            }
            public void setOnClickListener(ClickListener clickListener)
            {
                this.clickListener = clickListener;
            }
        }

        public interface ClickListener {
            void onItemClick(TrasaPunktowanaLite trasaPunktowanaLite);
        }
    }





}
