package com.web.demo.async;

import com.web.demo.download.DownloadGitHubFiles;
import com.web.demo.dtos.CountriesOldDTO;
import com.web.demo.utils.CommonUtils;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.function.Supplier;

public class ReadCountriesSupplier implements Supplier<List<CountriesOldDTO>> {
    @Override
    public List<CountriesOldDTO> get() {
        try {
            DownloadGitHubFiles.downloadFile("csv/CountriesRegions.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CountriesOldDTO> listCrop = null;
        try {
            listCrop = new CsvToBeanBuilder(new FileReader(CommonUtils.fileLocation() + "csv/CountriesRegions.csv"))
                    .withType(CountriesOldDTO.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listCrop;
    }
}
