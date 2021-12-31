package com.web.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.web.demo.dtos.CountriesDTO;
import com.web.demo.utils.IDemoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService{

    @Override
    public List<CountriesDTO> readAllCountries() {

        List<CountriesDTO> countryRegion = null;
        try {
            String fixture = IDemoUtils.readResource("AllCountries.json", Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            countryRegion = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CountriesDTO.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countryRegion;
    }

}
