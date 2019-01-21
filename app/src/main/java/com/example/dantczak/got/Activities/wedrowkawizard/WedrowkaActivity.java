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
import android.widget.EditText;
import android.widget.TextView;

import com.example.dantczak.got.Activities.SetupWalkActivity;
import com.example.dantczak.got.DTO.wedrowkawizard.PunktTrasyLite;
import com.example.dantczak.got.DTO.wedrowkawizard.TrasaPunktowanaLite;
import com.example.dantczak.got.DTO.wedrowkawizard.TrasaSkladowaLite;
import com.example.dantczak.got.DTO.wedrowkawizard.WedrowkaLite;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WedrowkaActivity extends AppCompatActivity {


    private static final  String WEDROWKA_URL  = "/wedrowka";

    private Button exitButton;
    private Button addAnotherTrasaButton;
    private RecyclerView wedrowkaRecyclerView;
    private TextView sumaPktTextView;
    private TextView sumaDistTextView;
    private EditText wedrowkaNameEditText;

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
        sumaDistTextView = findViewById(R.id.sum_dist_text);
        sumaPktTextView = findViewById(R.id.sum_pkt_text);
        wedrowkaNameEditText = findViewById(R.id.wedrowka_name_text);



        WedrowkaLite wedrowkaLite = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite();

        sumaDistTextView.setText(String.format("Suma km dotychczas: %.2f", wedrowkaLite.calcDistanceSum() ));
        sumaPktTextView.setText(String.format("Suma pkt dotychczas: %d", wedrowkaLite.calcPtsSum()));

        wedrowkaNameEditText.setText(wedrowkaLite.getNazwa()!=null? wedrowkaLite.getNazwa(): "Nazwa wędrówki");


        setupRecyclerView();



    }

    private void setupRecyclerView()
    {
        wedrowkaRecyclerView = findViewById(R.id.wedrowka_recycler_view);
        wedrowkaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(wedrowkaRecyclerView.getContext(), DividerItemDecoration.VERTICAL );
        wedrowkaRecyclerView.addItemDecoration(dividerItemDecoration);

        List<TrasaSkladowaLite> trasySkladowe = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite().getTrasySkladowe();

        populateTrasyViewWithData(createTrasaList(trasySkladowe));


    }

    private List<TrasaPunktowanaLite> createTrasaList(List<TrasaSkladowaLite> trasySkladowe) {
        List<TrasaPunktowanaLite> trasaPunktowanaLites = new ArrayList<>();
        for (TrasaSkladowaLite trasaSkladowaLite : trasySkladowe)
        {
            TrasaPunktowanaLite trasa = trasaSkladowaLite.getTrasaPunktowanaLite();
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

        wedrowkaNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite().setNazwa(wedrowkaNameEditText.getText().toString());
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WedrowkaLite wedrowkaLite = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite();
                wedrowkaLite.setNazwa(wedrowkaNameEditText.getText().toString());
                try {
                    HttpUtils.postWithBody(getApplicationContext(), WEDROWKA_URL, wedrowkaLite, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                } catch(Exception e)
                {
                    e.printStackTrace();
                }
                CreateWedrowkaSessionHolder.getInstance().clearWedrowka();

                Intent intent = new Intent(getApplicationContext(),SetupWalkActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setAddAnotherTrasaButtonClicked()
    {
        WedrowkaLite wedrowka = CreateWedrowkaSessionHolder.getInstance().getWedrowkaLite();
        List<TrasaSkladowaLite> trasySkladowe = wedrowka.getTrasySkladowe();
        TrasaSkladowaLite lastTrasa = trasySkladowe.get(trasySkladowe.size()-1);
        PunktTrasyLite punktKoncowyLastTrasa =  lastTrasa.getTrasaPunktowanaLite().getPunktKoncowy();

        Intent intent = new Intent(getApplicationContext(), ChooseOdcinekTrasyActivity.class);
        intent.putExtra(ChoosePunktPoczatkowyActivity.INTENT_PUNKT_ID, punktKoncowyLastTrasa.getPunktId());

        startActivity(intent);


    }


}
