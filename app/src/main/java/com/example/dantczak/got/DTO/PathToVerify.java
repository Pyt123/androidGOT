package com.example.dantczak.got.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PathToVerify
{
    private Long verifyPahtId;
    private Long touristId;
    private String touristName;
    private String touristSurname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date walkDate;
    private List<String> pathPointsNames;
    private List<String> pathPointsCoords;
    private List<byte[]> pics;
    private Integer rankPointsFor;
    private Boolean canModifyRankPoints;

    public PathToVerify() {}


    public Long getVerifyPahtId()
    {
        return verifyPahtId;
    }

    public void setVerifyPahtId(Long verifyPahtId)
    {
        this.verifyPahtId = verifyPahtId;
    }

    public Long getTouristId()
    {
        return touristId;
    }

    public void setTouristId(Long touristId)
    {
        this.touristId = touristId;
    }

    public String getTouristName()
    {
        return touristName;
    }

    public void setTouristName(String touristName)
    {
        this.touristName = touristName;
    }

    public String getTouristSurname()
    {
        return touristSurname;
    }

    public void setTouristSurname(String touristSurname)
    {
        this.touristSurname = touristSurname;
    }

    public Date getWalkDate()
    {
        return walkDate;
    }

    public void setWalkDate(Date walkDate)
    {
        this.walkDate = walkDate;
    }

    public List<String> getPathPointsNames()
    {
        return pathPointsNames;
    }

    public void setPathPointsNames(List<String> pathPointsNames)
    {
        this.pathPointsNames = pathPointsNames;
    }

    public List<String> getPathPointsCoords()
    {
        return pathPointsCoords;
    }

    public void setPathPointsCoords(List<String> pathPointsCoords)
    {
        this.pathPointsCoords = pathPointsCoords;
    }

    public List<byte[]> getPics()
    {
        return pics;
    }

    public void setPics(List<byte[]> pics)
    {
        this.pics = pics;
    }

    public Integer getRankPointsFor()
    {
        return rankPointsFor;
    }

    public void setRankPointsFor(Integer rankPointsFor)
    {
        this.rankPointsFor = rankPointsFor;
    }

    public Boolean getCanModifyRankPoints()
    {
        return canModifyRankPoints;
    }

    public void setCanModifyRankPoints(Boolean canModifyRankPoints)
    {
        this.canModifyRankPoints = canModifyRankPoints;
    }
}
