package com.example.dantczak.got.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class RankList
{
    private int reqTouristPosition;
    private List<String> names;
    private List<Integer> points;

    public RankList()
    {
        names = new ArrayList<>();
        points = new ArrayList<>();
    }

    public RankList(int length)
    {
        names = new ArrayList<>(length);
        points = new ArrayList<>(length);
    }

    public int getReqTouristPosition()
    {
        return reqTouristPosition;
    }

    public void setReqTouristPosition(int reqTouristPosition)
    {
        this.reqTouristPosition = reqTouristPosition;
    }

    public List<String> getNames()
    {
        return names;
    }

    public void setNames(List<String> names)
    {
        this.names = names;
    }

    public List<Integer> getPoints()
    {
        return points;
    }

    public void setPoints(List<Integer> points)
    {
        this.points = points;
    }
}