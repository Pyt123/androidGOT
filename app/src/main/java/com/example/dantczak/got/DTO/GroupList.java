package com.example.dantczak.got.DTO;

import java.util.ArrayList;
import java.util.List;

public class GroupList
{
    private List<Long> ids;
    private List<String> names;

    public GroupList()
    {
        ids = new ArrayList<>();
        names = new ArrayList<>();
    }

    public GroupList(int size)
    {
        ids = new ArrayList<>(size);
        names = new ArrayList<>(size);
    }

    public List<String> getNames()
    {
        return names;
    }

    public void setNames(List<String> names)
    {
        this.names = names;
    }

    public List<Long> getIds()
    {
        return ids;
    }

    public void setIds(List<Long> ids)
    {
        this.ids = ids;
    }

    public void add(Long id, String name)
    {
        ids.add(id);
        names.add(name);
    }
}
