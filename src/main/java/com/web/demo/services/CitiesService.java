package com.web.demo.services;

import com.web.demo.dtos.CitiesDTO;

import java.util.List;

public interface CitiesService {

    public List<CitiesDTO> readAllCities();

    public List<CitiesDTO> readAllCitiesByCountry(int countryId);
}
