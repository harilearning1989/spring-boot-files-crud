package com.web.demo.services;

import com.web.demo.dtos.CountriesDTO;

import java.util.List;

public interface CountriesService {

    public List<CountriesDTO> readAllCountries();
}
