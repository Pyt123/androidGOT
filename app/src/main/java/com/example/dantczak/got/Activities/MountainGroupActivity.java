package com.example.dantczak.got.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.dantczak.got.DTO.GroupList;
import com.example.dantczak.got.R;
import com.example.dantczak.got.Utils.HttpUtils;
import com.example.dantczak.got.Utils.JsonUtils;
import com.example.dantczak.got.Utils.ResponseHandlers.OnlySuccessMattersHandler;
import com.example.dantczak.got.Utils.TinyDb;
import com.fasterxml.jackson.databind.JavaType;

import java.util.ArrayList;

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
                    tinyDb.putListLong(getResources().getString(R.string.ranking_mountain_group_ids), adapter.getCheckedGroupIds());
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

                ArrayList<Long> newChecked = new ArrayList<>(adapter.getGroupList().getIds());
                newChecked.addAll(adapter.getGroupList().getIds());
                adapter.setCheckedGroupIds(newChecked);
            }
        });

        button = findViewById(R.id.odznacz_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter == null)
                    { return; }

                adapter.setCheckedGroupIds(new ArrayList<Long>());
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
        final ProgressDialog pd = ProgressDialog.show(MountainGroupActivity.this, "", "Pobieranie danych...", true);
        final Toast failToast = Toast.makeText(this, "Wystapił błąd... Nie można pobrać grup górskich.", Toast.LENGTH_LONG);
        try {
            HttpUtils.get("grupalite",
                    new OnlySuccessMattersHandler(pd, failToast) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try
                    {
                        super.onSuccess();
                        JavaType jt = JsonUtils.getObjectType("com.example.dantczak.got.DTO.GroupList");
                        GroupList result = JsonUtils.getObjectMapper().readValue(responseString, jt);
                        setupGroupList(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setupGroupList(GroupList groupList)
    {
        TinyDb tinyDb = new TinyDb(this);
        ArrayList<Long> checked = new ArrayList<>(0);
        try { checked = tinyDb.getListLong(getResources().getString(R.string.ranking_mountain_group_ids)); }
        catch (Exception e) { e.printStackTrace(); }

        RecyclerView recyclerView = findViewById(R.id.groups_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MountainGroupsAdapter(groupList, checked, this);
        recyclerView.setAdapter(adapter);
    }
}

class MountainGroupsAdapter extends RecyclerView.Adapter<MountainGroupsAdapter.ViewHolder>
{
    private GroupList groupList;
    private ArrayList<Long> checkedGroupIds;
    private Context context;

    public MountainGroupsAdapter(GroupList groupList, ArrayList<Long> checkedGroupIds, Context context) {
        this.groupList = groupList;
        this.checkedGroupIds = checkedGroupIds;
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
        int index = checkedGroupIds.indexOf(groupList.getIds().get(position));
        viewHolder.setChecked(index >= 0);
        viewHolder.setText(groupList.getNames().get(position));
    }

    @Override
    public int getItemCount()
    {
        if(groupList.getIds() == null)
        {
            return 0;
        }
        else
        {
            return groupList.getIds().size();
        }
    }

    public GroupList getGroupList()
    {
        return groupList;
    }

    public void setGroupList(GroupList groupList)
    {
        this.groupList = groupList;
    }

    public ArrayList<Long> getCheckedGroupIds()
    {
        return checkedGroupIds;
    }

    public void setCheckedGroupIds(ArrayList<Long> checkedGroupIds)
    {
        this.checkedGroupIds = checkedGroupIds;
        notifyDataSetChanged();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private CheckBox checkedTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.group_checkbox);
            checkedTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                }
            });
        }

        public boolean isChecked()
        {
            return checkedTextView.isChecked();
        }

        public void setChecked(boolean checked)
        {
            checkedTextView.setChecked(checked);
        }

        public void setText(String name)
        {
            checkedTextView.setText(name);
        }
    }
}