package com.web.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.web.demo.dtos.CitiesDTO;
import com.web.demo.utils.IDemoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService{

    @Override
    public List<CitiesDTO> readAllCities() {

        List<CitiesDTO> countryRegion = null;
        try {
            String fixture = IDemoUtils.readResource("AllCities9.json", Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            countryRegion = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CitiesDTO.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countryRegion;
    }

    @Override
    public List<CitiesDTO> readAllCitiesByCountry(int countryId) {
        List<CitiesDTO> countryRegion = null;
        try {
            String fixture = "";
            if(countryId <=90){
                fixture = IDemoUtils.readResource("AllCities9.json", Charsets.UTF_8);
            }else if(countryId <= 170){
                fixture = IDemoUtils.readResource("AllCities17.json", Charsets.UTF_8);
            }else{
                fixture = IDemoUtils.readResource("AllCities25.json", Charsets.UTF_8);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            countryRegion = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CitiesDTO.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countryRegion;
    }

}
