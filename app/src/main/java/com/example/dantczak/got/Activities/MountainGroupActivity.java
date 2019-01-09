package com.example.dantczak.got.Activities;

import android.content.Context;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.TinyDb;
import com.example.dantczak.got.model.grupa.GrupaGorska;
import com.fasterxml.jackson.databind.JavaType;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MountainGroupActivity extends AppCompatActivity
{
    private MountainGroupsAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupButtons();
        setupGroups();
    }

    private void setupButtons()
    {
        Button button = findViewById(R.id.confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter != null)
                {
                    TinyDb tinyDb = new TinyDb(getApplicationContext());
                    tinyDb.putListLong(getResources().getString(R.string.ranking_mountain_group_ids), adapter.getCheckedGroups());
                    finish();
                }
            }
        });

        button = findViewById(R.id.zanacz_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter == null)
                    { return; }

                ArrayList<Long> newChecked = new ArrayList<>(adapter.getGroupList().size());
                for(GrupaGorska gg : adapter.getGroupList())
                {
                    newChecked.add(gg.getId());
                }
                adapter.setCheckedGroups(newChecked);
            }
        });

        button = findViewById(R.id.odznacz_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter == null)
                    { return; }

                adapter.setCheckedGroups(new ArrayList<Long>());
            }
        });
    }

    private void setupView() {
        setContentView(R.layout.activity_mountain_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupGroups() {
        try {
            HttpUtils.get("grupa",
                    new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.v("request failure", responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {
                                JavaType jt = JsonUtils.getGenericListType("com.example.dantczak.got.model.grupa.GrupaGorska");
                                List<GrupaGorska> result = JsonUtils.getObjectMapper().readValue(responseString, jt);
                                setupGroupList(result);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setupGroupList(List<GrupaGorska> groupList)
    {
        TinyDb tinyDb = new TinyDb(this);
        ArrayList<Long> checked = tinyDb.getListLong(getResources().getString(R.string.ranking_mountain_group_ids));

        RecyclerView recyclerView = findViewById(R.id.groups_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MountainGroupsAdapter(groupList, checked, this);
        recyclerView.setAdapter(adapter);
        adapter.setGroupList(groupList);
    }
}

class MountainGroupsAdapter extends RecyclerView.Adapter<MountainGroupsAdapter.ViewHolder>
{
    private List<GrupaGorska> groupList;
    private ArrayList<Long> checkedGroups;
    private Context context;

    public MountainGroupsAdapter(List<GrupaGorska> groupList, ArrayList<Long> checkedGroups, Context context)
    {
        this.setGroupList(groupList);
        this.setCheckedGroups(checkedGroups);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mountain_group_element, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        GrupaGorska group = getGroupList().get(position);
        int index = getCheckedGroups().indexOf(group);
        viewHolder.setChecked(index >= 0);
        viewHolder.setText(group.getNazwaGrupy());
        viewHolder.setBindedGroup(group);
    }

    @Override
    public int getItemCount()
    {
        if(getGroupList() == null)
        {
            return 0;
        }
        else
        {
            return getGroupList().size();
        }
    }

    public void setGroupList(List<GrupaGorska> groupList) {
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    public ArrayList<Long> getCheckedGroups() {
        return checkedGroups;
    }

    public void setCheckedGroups(ArrayList<Long> checkedGroups) {
        this.checkedGroups = checkedGroups;
        notifyDataSetChanged();
    }

    public List<GrupaGorska> getGroupList() {
        return groupList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private CheckBox checkedTextView;
        private GrupaGorska bindedGroup;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.group_checkbox);
            checkedTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(bindedGroup == null)
                        { return; }

                    if(isChecked)
                    {
                        getCheckedGroups().add(bindedGroup.getId());
                    }
                    else
                    {
                        getCheckedGroups().remove(bindedGroup.getId());
                    }
                }
            });
        }


        public boolean isChecked() {
            return checkedTextView.isChecked();
        }

        public void setChecked(boolean checked)
        {
            checkedTextView.setChecked(checked);
        }

        public void setText(String name) {
            checkedTextView.setText(name);
        }

        public void setBindedGroup(GrupaGorska bindedGroup) {
            this.bindedGroup = bindedGroup;
        }
    }
}