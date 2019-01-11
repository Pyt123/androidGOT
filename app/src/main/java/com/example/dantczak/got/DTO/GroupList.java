package com.example.dantczak.got.DTO;

import java.util.List;

public class GroupList
{
    private List<Long> ids;
    private List<String> names;

    public List<String> getNames()
    {
        return names;
    }

    public void setName(List<String> names)
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
}
