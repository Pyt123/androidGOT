package com.example.dantczak.got.Activities.ulozwedrowke;

import android.content.Context;
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

import com.example.dantczak.got.DTO.RankList;
import com.example.dantczak.got.DTO.ulozwedrowke.GrupaGorska;
import com.example.dantczak.got.DTO.ulozwedrowke.PunktTrasyDTO;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ChoosePunktPoczatkowyActivity extends AppCompatActivity {

    private static String FETCH_GRUPY_URL = "/grupa/notempty";
    private static String FETCH_PUNKTY_URL = "/lite/punkt/poczatkowy/wgrupie?grupa=";

    private SearchView searchView;
    private Spinner grupaGorskaSpinner;
    private RecyclerView punktyRecyclerView;

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
    private void populatePunktyViewWithData(List<PunktTrasyDTO> punktyTrasy) {

        if (punktyRecyclerView.getAdapter()==null) {
            PunktAdapter adapter = new PunktAdapter(punktyTrasy, this);
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
                String nazwaGrupy =  (String) parent.getAdapter().getItem(position);
                fetchPunktyInGrupa(nazwaGrupy);
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
                    List<PunktTrasyDTO> punktyTrasy = JsonUtils.getObjectMapper().readValue(responseString, JsonUtils.getListType(PunktTrasyDTO.class));
                    populatePunktyViewWithData(punktyTrasy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }




    static class PunktAdapter extends RecyclerView.Adapter<PunktAdapter.ViewHolder>
    {
        private List<PunktTrasyDTO> punktyTrasy;
        private Context context;

        public PunktAdapter(List<PunktTrasyDTO> punktyTrasy, Context context)
        {
            this.punktyTrasy = punktyTrasy;
            this.context = context;
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

        public List<PunktTrasyDTO> getPunktyTrasy() {
            return punktyTrasy;
        }

        public void setPunktyTrasy(List<PunktTrasyDTO> punktyTrasy) {
            this.punktyTrasy = punktyTrasy;
        }


        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView nazwaPunktu;
            private TextView nazwaGrupy;
            private LinearLayout punkty_row_ll;

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

        }
    }





}
