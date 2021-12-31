package com.web.demo.controls;

import com.web.demo.dtos.CountriesDTO;
import com.web.demo.services.CountriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("countrries")
@CrossOrigin(origins = "http://localhost:4200")
public class CountriesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CropRestController.class);

    CountriesService countriesService;

    @Autowired
    public void setCountriesService(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CountriesDTO>> readAllCountries() {
        LOGGER.info("readAllCountries======");

        CompletableFuture<List<CountriesDTO>> countryFuture =
                supplyAsync(() -> countriesService.readAllCountries());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(countryFuture.get());
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("CountriesRestController readAllCountries======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
