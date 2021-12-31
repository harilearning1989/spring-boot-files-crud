package com.web.demo.controls;

import com.web.demo.dtos.CommonDTO;
import com.web.demo.dtos.CropInsuranceDTO;
import com.web.demo.dtos.SalesOrderDTO;
import com.web.demo.dtos.StudentDTO;
import com.web.demo.services.CropService;
import com.web.demo.services.SalesService;
import com.web.demo.services.StudentService;
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
@RequestMapping("common")
@CrossOrigin(origins = "http://localhost:4200")
public class CommonRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonRestController.class);

    CropService cropService;

    @Autowired
    public void setCropService(CropService cropService) {
        this.cropService = cropService;
    }

    SalesService salesService;
    @Autowired
    public void setSalesService(SalesService salesService) {
        this.salesService = salesService;
    }

    StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/readAll")
    public ResponseEntity<CommonDTO> readAllData() {
        LOGGER.info("readAllData======");

        CompletableFuture<List<CropInsuranceDTO>> cropFuture =
                supplyAsync(() -> cropService.readCropDetails());
        CompletableFuture<List<SalesOrderDTO>> salesFuture =
                supplyAsync(() -> salesService.readSalesDetails());
        CompletableFuture<List<StudentDTO>> studentFuture =
                supplyAsync(() -> studentService.readStudentDetails());
        try {
            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setCropDto(cropFuture.get());
            commonDTO.setSalesDto(salesFuture.get());
            commonDTO.setStudentDto(studentFuture.get());
            return ResponseEntity.status(HttpStatus.OK).body(commonDTO);
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("CommonRestController readAllData======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
