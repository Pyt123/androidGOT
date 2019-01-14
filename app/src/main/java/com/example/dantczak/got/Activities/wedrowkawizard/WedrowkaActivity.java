package com.example.dantczak.got.Activities.wedrowkawizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.dantczak.got.DTO.wedrowkawizard.PunktTrasyLite;
import com.example.dantczak.got.DTO.wedrowkawizard.TrasaPunktowanaLite;
import com.example.dantczak.got.DTO.wedrowkawizard.TrasaSkladowa;
import com.example.dantczak.got.DTO.wedrowkawizard.WedrowkaLite;
import com.example.dantczak.got.R;

import java.util.ArrayList;
import java.util.List;

public class WedrowkaActivity extends AppCompatActivity {

    private Button exitButton;
    private Button addAnotherTrasaButton;
    private RecyclerView wedrowkaRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();
        setupListeners();


    }

    private void setupViews()
    {
        setContentView(R.layout.activity_wedrowka);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exitButton = findViewById(R.id.zakoncz_button);
        addAnotherTrasaButton = findViewById(R.id.add_trasa_button);

        setupRecyclerView();



    }

    private void setupRecyclerView()
    {
        wedrowkaRecyclerView = findViewById(R.id.wedrowka_recycler_view);
        wedrowkaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(wedrowkaRecyclerView.getContext(), DividerItemDecoration.VERTICAL );
        wedrowkaRecyclerView.addItemDecoration(dividerItemDecoration);

        List<TrasaSkladowa> trasySkladowe = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite().getTrasySkladowe();

        populateTrasyViewWithData(createTrasaList(trasySkladowe));


    }

    private List<TrasaPunktowanaLite> createTrasaList(List<TrasaSkladowa> trasySkladowe) {
        List<TrasaPunktowanaLite> trasaPunktowanaLites = new ArrayList<>();
        for (TrasaSkladowa trasaSkladowa : trasySkladowe)
        {
            TrasaPunktowanaLite trasa = trasaSkladowa.getTrasaPunktowanaLite();
            trasaPunktowanaLites.add(trasa);
        }
        return trasaPunktowanaLites;
    }


    private void populateTrasyViewWithData(List<TrasaPunktowanaLite> trasy) {

        if (wedrowkaRecyclerView.getAdapter()==null) {
            ChooseOdcinekTrasyActivity.TrasaAdapter adapter = new ChooseOdcinekTrasyActivity.TrasaAdapter(trasy, getApplicationContext(), new ChooseOdcinekTrasyActivity.TrasaAdapter.ClickListener() {
                @Override
                public void onItemClick(TrasaPunktowanaLite trasaPunktowanaLite) {

                }
            });
            wedrowkaRecyclerView.setAdapter(adapter);
        } else
        {
            ChooseOdcinekTrasyActivity.TrasaAdapter trasaAdapter = (ChooseOdcinekTrasyActivity.TrasaAdapter) wedrowkaRecyclerView.getAdapter();
            trasaAdapter.setTrasyPunktowane(trasy);
            trasaAdapter.notifyDataSetChanged();

        }
    }


    private void setupListeners()
    {
        addAnotherTrasaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAddAnotherTrasaButtonClicked();
            }
        });
    }

    private void setAddAnotherTrasaButtonClicked()
    {
        WedrowkaLite wedrowka = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite();
        List<TrasaSkladowa> trasySkladowe = wedrowka.getTrasySkladowe();
        TrasaSkladowa lastTrasa = trasySkladowe.get(trasySkladowe.size()-1);
        PunktTrasyLite punktKoncowyLastTrasa =  lastTrasa.getTrasaPunktowanaLite().getPunktKoncowy();

        Intent intent = new Intent(getApplicationContext(), ChooseOdcinekTrasyActivity.class);
        intent.putExtra(ChoosePunktPoczatkowyActivity.INTENT_PUNKT_ID, punktKoncowyLastTrasa.getPunktId());

        startActivity(intent);


    }


}
