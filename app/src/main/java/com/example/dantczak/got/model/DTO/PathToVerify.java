package com.example.dantczak.got.model.DTO;

import com.example.dantczak.got.model.Wedrowka.Pamiatka;
import com.example.dantczak.got.model.Wedrowka.TrasaSkladowa;
import com.example.dantczak.got.model.Wedrowka.Wedrowka;
import com.example.dantczak.got.model.trasa.SkladowyPunktTrasy;
import com.example.dantczak.got.model.trasa.TrasaNiepunktowana;
import com.example.dantczak.got.model.uzytkownik.Turysta;
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
    private List<SkladowyPunktTrasy> pahtPoints;
    private List<Pamiatka> pics;
    private Integer pointsFor;
    private Boolean canModifyPoints;

    public PathToVerify() {}

    public PathToVerify(Turysta tourist, Wedrowka walk, TrasaSkladowa componentPath)
    {
        verifyPahtId = componentPath.getId();
        touristId = tourist.getId();
        touristName = tourist.getImie();
        touristSurname = tourist.getNazwisko();
        walkDate = walk.getDataWedrowki();
        pahtPoints = componentPath.getTrasa().getSkladowePunktyTrasy();
        pics = componentPath.getPamiatki();
        pointsFor = componentPath.getTrasa().getLiczbaPunktow();
        canModifyPoints = componentPath.getTrasa() instanceof TrasaNiepunktowana;
    }

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

    public List<SkladowyPunktTrasy> getPahtPoints()
    {
        return pahtPoints;
    }

    public void setPahtPoints(List<SkladowyPunktTrasy> pahtPoints)
    {
        this.pahtPoints = pahtPoints;
    }

    public List<Pamiatka> getPics()
    {
        return pics;
    }

    public void setPics(List<Pamiatka> pics)
    {
        this.pics = pics;
    }

    public Integer getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(Integer pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Boolean getCanModifyPoints() {
        return canModifyPoints;
    }

    public void setCanModifyPoints(Boolean canModifyPoints) {
        this.canModifyPoints = canModifyPoints;
    }
}
