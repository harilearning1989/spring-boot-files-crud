package com.web.demo.controls;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.web.demo.constants.IConstants;
import com.web.demo.dtos.*;
import com.web.demo.services.CSVReadService;
import com.web.demo.utils.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("csv")
public class CSVReadRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVReadRestController.class);

    @Value("${csv.read.readCsv}")
    private String load;

    @Autowired
    private CSVReadService csvReadService;

    @GetMapping(value = "/states")
    public List<IndiaStatesDTO> getIndiaStates() {
        CompletableFuture<List<IndiaStatesDTO>> empFuture =
                supplyAsync(() -> csvReadService.getIndiaStates());
        try {
            return empFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readEmp")
    public List<EmployeeDTO> readEmpCSV() {
        CompletableFuture<List<EmployeeDTO>> empFuture =
                supplyAsync(() -> csvReadService.readEmployeeInfo());
        try {
            return empFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readCrop")
    @CrossOrigin
    public ResponseEntity<List<CropInsuranceDTO>> readCropCSV() {
        CompletableFuture<List<CropInsuranceDTO>> cropFuture =
                supplyAsync(() -> csvReadService.readCropDetails());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cropFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readCrop/{mandalName}")
    @CrossOrigin
    public ResponseEntity<List<CropInsuranceDTO>> readCropByMandal(@PathVariable String mandalName) {
        CompletableFuture<List<CropInsuranceDTO>> cropFuture =
                supplyAsync(() -> csvReadService.readCropByMandal(mandalName));
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cropFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readStudent")
    public List<StudentDTO> readStudentCSV() {
        CompletableFuture<List<StudentDTO>> studentFuture =
                supplyAsync(() -> csvReadService.readStudentInfo());
        try {
            return studentFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readCountry")
    public List<CountriesOldDTO> readCountryCSV() {
        CompletableFuture<List<CountriesOldDTO>> countriesFuture =
                supplyAsync(() -> csvReadService.readCountriesRegions());
        try {
            return countriesFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readSales")
    public List<SalesOrderDTO> readSalesCSV() {
        CompletableFuture<List<SalesOrderDTO>> salesFuture =
                supplyAsync(() -> csvReadService.readSalesOrderDetails());
        try {
            return salesFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "uploadCSV")
    public List<TutorialDTO> uploadCSV(@RequestParam("file") MultipartFile file) {
        List<TutorialDTO> tutorials = null;
        try {
            tutorials = CSVHelper.csvToTutorials(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tutorials;
    }

    @GetMapping(value = "load")
    public List<List<String>> readCsv() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(IConstants.SAMPLE_CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                LOGGER.info(String.format("The Line===%1$s", line));
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    @GetMapping(value = "openCSV")
    public String readOpenCSV() {
        //CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();  //skip Header Row
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(IConstants.SAMPLE_CSV_FILE_PATH));) {
            String[] line = null;
            while ((line = csvReader.readNext()) != null) {
                LOGGER.info(String.format("Line is===%1$s", line));
                records.add(Arrays.asList(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return "readOpenCSV";
    }

    @GetMapping(value = "userCsv")
    public String readUserCsv() {
        try (Reader reader = Files.newBufferedReader(Paths.get(IConstants.USERS_WITH_HEADER));
             CSVReader csvReader = new CSVReader(reader);) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
               /* LOGGER.info(String.format("Line ===%1$s", line));
                LOGGER.info(String.format("Name : %1$s", line[0]));
                LOGGER.info(String.format("Email : %1$s", line[1]));
                LOGGER.info(String.format("Phone : %1$s", line[2]));
                LOGGER.info(String.format("Country : %1$s", line[3]));
                LOGGER.info("==========================");*/
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return "readUserCsv";
    }

    @GetMapping(value = "readCSVToMode")
    public List<CSVUserDTO> readCSVToMode() {
        Iterator<CSVUserDTO> csvUserIterator = null;
        List<CSVUserDTO> usersList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(IConstants.USERS_WITH_HEADER));) {
            CsvToBean<CSVUserDTO> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVUserDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            csvUserIterator = csvToBean.iterator();

            while (csvUserIterator.hasNext()) {
                CSVUserDTO csvUser = csvUserIterator.next();
                LOGGER.info(String.format("Name : %1$s", csvUser.getName()));
                LOGGER.info(String.format("Email : %1$s", csvUser.getEmail()));
                LOGGER.info(String.format("PhoneNo : %1$s", csvUser.getPhoneNo()));
                LOGGER.info(String.format("Country : %1$s", csvUser.getCountry()));
                LOGGER.info("==========================");
                usersList.add(new CSVUserDTO(csvUser.getName(), csvUser.getEmail(), csvUser.getPhoneNo(), csvUser.getCountry()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

}
