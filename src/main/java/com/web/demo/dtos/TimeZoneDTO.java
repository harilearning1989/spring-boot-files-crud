package com.web.demo.dtos;

public class TimeZoneDTO {

    private String zoneName;
    private int gmtOffset;
    private String gmtOffsetName;
    private String abbreviation;
    private String tzName;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(int gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getGmtOffsetName() {
        return gmtOffsetName;
    }

    public void setGmtOffsetName(String gmtOffsetName) {
        this.gmtOffsetName = gmtOffsetName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getTzName() {
        return tzName;
    }

    public void setTzName(String tzName) {
        this.tzName = tzName;
    }

    @Override
    public String toString() {
        return "TimeZoneDTO{" +
                "zoneName='" + zoneName + '\'' +
                ", gmtOffset=" + gmtOffset +
                ", gmtOffsetName='" + gmtOffsetName + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", tzName='" + tzName + '\'' +
                '}';
    }
}
