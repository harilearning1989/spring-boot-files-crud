package com.web.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.web.demo.dtos.CropInsuranceDTO;
import com.web.demo.utils.IDemoUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class CropServiceImpl implements CropService {

    @Override
    public List<CropInsuranceDTO> readCropDetails() {

        List<CropInsuranceDTO> countryRegion = null;
        try {
            String fixture = IDemoUtils.readResource("crops.json", Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            countryRegion = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CropInsuranceDTO.class));
            countryRegion = Optional.ofNullable(countryRegion)
                    .orElseGet(Collections::emptyList)
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .filter(f -> f.getVillageName() != null && f.getNameOfTheBeneficiary() != null)
                    .sorted(comparing(CropInsuranceDTO::getVillageName, String::compareToIgnoreCase)
                            .thenComparing(CropInsuranceDTO::getNameOfTheBeneficiary)
                            .thenComparing(CropInsuranceDTO::getCrop)
                            .thenComparing(CropInsuranceDTO::getClaimAmountRs))
                    .collect(toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return countryRegion;
    }
}
