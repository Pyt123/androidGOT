package com.example.dantczak.got.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PathToVerify
{
    private Long verifyPathId;
    private Long touristId;
    private String touristName;
    private String touristSurname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date walkDate;
    private List<Pair<String, String>> pointNamesAndCords;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<byte[]> pics;
    private Integer rankPointsFor;
    private Boolean canModifyRankPoints;

    public PathToVerify() {}

    public PathToVerify(Long verifyPathId, Long touristId, String touristName, String touristSurname,
                        Date walkDate, List<Pair<String, String>> pointNamesAndCords,
                        List<byte[]> pics, Integer rankPointsFor, Boolean canModifyRankPoints)
    {
        this.verifyPathId = verifyPathId;
        this.touristId = touristId;
        this.touristName = touristName;
        this.touristSurname = touristSurname;
        this.walkDate = walkDate;
        this.pointNamesAndCords = pointNamesAndCords;
        this.pics = pics;
        this.rankPointsFor = rankPointsFor;
        this.canModifyRankPoints = canModifyRankPoints;
    }

    public Long getVerifyPathId()
    {
        return verifyPathId;
    }

    public void setVerifyPathId(Long verifyPathId)
    {
        this.verifyPathId = verifyPathId;
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

    public List<Pair<String, String>> getPointNamesAndCords()
    {
        return pointNamesAndCords;
    }

    public void setPointNamesAndCords(List<Pair<String, String>> pointNamesAndCords)
    {
        this.pointNamesAndCords = pointNamesAndCords;
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
