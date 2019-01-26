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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dantczak.got.DTO.wedrowkawizard.GrupaGorska;
import com.example.dantczak.got.DTO.wedrowkawizard.PunktTrasyLite;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ChoosePunktPoczatkowyActivity extends AppCompatActivity {

    private static String FETCH_GRUPY_URL = "/grupa/notempty";
    private static String FETCH_PUNKTY_URL = "/lite/punkt/poczatkowy/wgrupie?grupa=";
    public static String INTENT_PUNKT_ID = "punktId";

    private SearchView searchView;
    private Spinner grupaGorskaSpinner;
    private RecyclerView punktyRecyclerView;
    private String chosenNazwaGrupy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        fetchGroupNames();
        setupSpinnerListener();
    }

    private void setupView() {
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_setup_walk_choose_punkt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);
        grupaGorskaSpinner = findViewById(R.id.grupaGorskaSpinner);

        setupRecyclerView();

    }

    private void setupRecyclerView()
    {
        punktyRecyclerView = findViewById(R.id.punktyRecyclerView);
        punktyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(punktyRecyclerView.getContext(), DividerItemDecoration.VERTICAL );
        punktyRecyclerView.addItemDecoration(dividerItemDecoration);
    }


    private void populateSpinnerWithData(List<String> groupNames) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupNames);
        grupaGorskaSpinner.setAdapter(dataAdapter);
    }
    private void populatePunktyViewWithData(List<PunktTrasyLite> punktyTrasy) {

        if (punktyRecyclerView.getAdapter()==null) {
            final PunktAdapter adapter = new PunktAdapter(punktyTrasy, this, new PunktAdapter.ClickListener() {
                @Override
                public void onItemClick(PunktTrasyLite punkt) {
                    long punktId = punkt.getPunktId();
                    Intent intent = new Intent(getApplicationContext(), ChooseOdcinekTrasyActivity.class);
                    intent.putExtra(INTENT_PUNKT_ID, punktId);
                    CreateWedrowkaSessionHolder.getInstance().createWedrowka();

                    startActivity(intent);
                }
            });
            punktyRecyclerView.setAdapter(adapter);

        } else
        {
           PunktAdapter punktAdapter = (PunktAdapter) punktyRecyclerView.getAdapter();
           punktAdapter.setPunktyTrasy(punktyTrasy);
           punktAdapter.notifyDataSetChanged();

        }
    }

    private void setupSpinnerListener()
    {
        grupaGorskaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenNazwaGrupy =  (String) parent.getAdapter().getItem(position);
                fetchPunktyInGrupa(chosenNazwaGrupy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private  void fetchGroupNames()
    {

        HttpUtils.get(FETCH_GRUPY_URL, new OnlySuccessMattersHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    List<String> groupNames = new ArrayList<String>();
                    List<GrupaGorska> result = JsonUtils.getObjectMapper().readValue(responseString, JsonUtils.getListType(GrupaGorska.class));
                    for (GrupaGorska grupa : result )
                    {
                        groupNames.add(grupa.getNazwaGrupy());
                    }
                    populateSpinnerWithData(groupNames);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

    }

    private void fetchPunktyInGrupa(String nazwaGrupy)
    {
        HttpUtils.get(FETCH_PUNKTY_URL + nazwaGrupy, new OnlySuccessMattersHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    List<PunktTrasyLite> punktyTrasy = JsonUtils.getObjectMapper().readValue(responseString, JsonUtils.getListType(PunktTrasyLite.class));
                    populatePunktyViewWithData(punktyTrasy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }




    static class PunktAdapter extends RecyclerView.Adapter<PunktAdapter.ViewHolder>
    {
        private List<PunktTrasyLite> punktyTrasy;
        private Context context;
        private ClickListener clickListener;

        public PunktAdapter(List<PunktTrasyLite> punktyTrasy, Context context, ClickListener clickListener)
        {
            this.punktyTrasy = punktyTrasy;
            this.context = context;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.punkty_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
        {

            String nazwaGrupy = punktyTrasy.get(position).getNazwaGrupy();
            viewHolder.nazwaGrupy.setText(nazwaGrupy);
            String nazwaPunktu = punktyTrasy.get(position).getNazwaPunktu();
            viewHolder.nazwaPunktu.setText(nazwaPunktu);
            viewHolder.setOnItemClickListener(clickListener);
        }

        @Override
        public int getItemCount()
        {
            if(punktyTrasy == null)
            {
                return 0;
            }
            else
            {
                return punktyTrasy.size();
            }
        }

        public PunktTrasyLite getPunktAtPos(int pos)
        {
            return punktyTrasy.get(pos);
        }

        public List<PunktTrasyLite> getPunktyTrasy() {
            return punktyTrasy;
        }

        public void setPunktyTrasy(List<PunktTrasyLite> punktyTrasy) {
            this.punktyTrasy = punktyTrasy;
        }



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private TextView nazwaPunktu;
            private TextView nazwaGrupy;
            private LinearLayout punkty_row_ll;
            private ClickListener clickListener;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                nazwaPunktu = itemView.findViewById(R.id.nazwa_punktu_text);
                nazwaGrupy = itemView.findViewById(R.id.nazwa_grupy_text);
                punkty_row_ll = itemView.findViewById(R.id.punkty_row_ll);
            }

            public TextView getNazwaPunktu() {
                return nazwaPunktu;
            }


            public TextView getNazwaGrupy() {
                return nazwaGrupy;
            }

            public LinearLayout getPunkty_row_ll() {
                return punkty_row_ll;
            }

            @Override
            public void onClick(View v) {
                if (clickListener!=null)
                clickListener.onItemClick(getPunktAtPos(getAdapterPosition()));
            }

            public void setOnItemClickListener(ClickListener clickListener)
            {
                this.clickListener = clickListener;
                itemView.setOnClickListener(this);
            }
        }

        public interface ClickListener {
            void onItemClick(PunktTrasyLite punkt);
        }
    }





}
