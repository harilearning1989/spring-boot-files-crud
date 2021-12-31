package com.web.demo.controls;

import com.web.demo.dtos.CitiesDTO;
import com.web.demo.services.CitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("cities")
@CrossOrigin(origins = "http://localhost:4200")
public class CitiesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CropRestController.class);

    CitiesService citiesService;

    @Autowired
    public void setCitiesService(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CitiesDTO>> readAllCities() {
        LOGGER.info("readAllCities======");

        CompletableFuture<List<CitiesDTO>> countryFuture =
                supplyAsync(() -> citiesService.readAllCities());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(countryFuture.get());
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("CountriesRestController readAllCountries======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/byCountry/{countryId}")
    public ResponseEntity<List<CitiesDTO>> readAllCitiesByCountry(@PathVariable int countryId) {
        LOGGER.info("readAllCitiesByCountry======"+countryId);

        CompletableFuture<List<CitiesDTO>> countryFuture =
                supplyAsync(() -> citiesService.readAllCitiesByCountry(countryId));
        try {
            List<CitiesDTO> citiesDTOList = Optional.ofNullable(countryFuture.get())
                    .orElseGet(Collections::emptyList)
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .filter(f -> f.getCountryId() == countryId)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(citiesDTOList);
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("CountriesRestController readAllCountries======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
