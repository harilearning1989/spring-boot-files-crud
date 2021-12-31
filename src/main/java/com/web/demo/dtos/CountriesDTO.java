package com.web.demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountriesDTO {

    private int id;
    private String name;
    @JsonProperty("numcode")
    private String numericCode;
    private String phonecode;
    private String currency;
    private String region;
    private String subregion;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty( value = "timezones", access = JsonProperty.Access.WRITE_ONLY)
    private String timezones;
    private Map<String,List<TimeZoneDTO>> timezonesMap = new HashMap<>();
    private List<TimeZoneDTO> timezonesList;
    private int timeZoneSize;
    private String emoji;
    private String capital;

    public int getTimeZoneSize() {
        return timeZoneSize;
    }

    public void setTimeZoneSize(int timeZoneSize) {
        this.timeZoneSize = timeZoneSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TimeZoneDTO> timeZoneDTO = objectMapper.readValue(timezones, new TypeReference<List<TimeZoneDTO>>(){});
            //System.out.println(timeZoneDTO);
            timezonesMap.put(this.name,timeZoneDTO);
            setTimezonesList(timeZoneDTO);
            setTimeZoneSize(timeZoneDTO.size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.timezones = timezones;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    /*public Map<String, List<TimeZoneDTO>> getTimezonesMap() {
        return timezonesMap;
    }*/

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<TimeZoneDTO> getTimezonesList() {
        return timezonesList;
    }

    public void setTimezonesList(List<TimeZoneDTO> timezonesList) {
        this.timezonesList = timezonesList;
    }
}
