package com.example.dantczak.got.DTO;


import java.util.ArrayList;
import java.util.List;

public class RankList
{
    private int reqTouristIndex = -1;
    private List<Integer> positions;
    private List<Pair<String, Integer>> entries;

    public RankList()
    {
        positions = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public RankList(int length)
    {
        positions = new ArrayList<>(length);
        entries = new ArrayList<>(length);
    }

    public int getReqTouristIndex()
    {
        return reqTouristIndex;
    }

    public void setReqTouristIndex(int reqTouristIndex)
    {
        this.reqTouristIndex = reqTouristIndex;
    }

    public List<Pair<String, Integer>> getEntries()
    {
        return entries;
    }

    public void setEntries(List<Pair<String, Integer>> entries)
    {
        this.entries = entries;
    }

    public void addEntry(Integer position, String name, Integer points)
    {
        positions.add(position);
        entries.add(new Pair<>(name, points));
    }

    public List<Integer> getPositions()
    {
        return positions;
    }

    public void setPositions(List<Integer> positions)
    {
        this.positions = positions;
    }
}