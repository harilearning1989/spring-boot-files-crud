package com.web.demo.controls;

import com.web.demo.dtos.SalesOrderDTO;
import com.web.demo.dtos.StudentDTO;
import com.web.demo.dtos.StudentMarksDTO;
import com.web.demo.dtos.StudentSaleDTO;
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
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRestController.class);

    StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/readStudents")
    public ResponseEntity<List<StudentDTO>> readStudentCSV() {
        CompletableFuture<List<StudentDTO>> cropFuture =
                supplyAsync(() -> studentService.readStudentDetails());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cropFuture.get());
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("StudentRestController readStudentCSV======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/marks")
    public ResponseEntity<List<StudentMarksDTO>> readStudentWithMarks() {
        CompletableFuture<List<StudentMarksDTO>> cropFuture =
                supplyAsync(() -> studentService.readStudentWithMarks());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cropFuture.get());
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("StudentRestController readStudentCSV======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/readStudents/marks")
    public ResponseEntity<List<StudentMarksDTO>> readStudentMarks() {
        CompletableFuture<List<StudentDTO>> cropFuture =
                supplyAsync(() -> studentService.readStudentDetails());
        try {
            List<StudentMarksDTO> marksDTOList = Optional.ofNullable(cropFuture.get())
                    .orElseGet(Collections::emptyList)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(m -> {
                        return setStudentData(m);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(marksDTOList);
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            LOGGER.info("StudentRestController readStudentCSV======"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private StudentMarksDTO setStudentData(StudentDTO m) {
        StudentMarksDTO marksDTO = new StudentMarksDTO();
        marksDTO.setStudentId(m.getStudentId());
        marksDTO.setCategory(m.getCategory());
        marksDTO.setFatherName(m.getFatherName());
        marksDTO.setGender(m.getGender());
        marksDTO.setMobile(m.getMobile());
        marksDTO.setStudentName(m.getStudentName());

        if(marksDTO.getCategory().equalsIgnoreCase("OC")
                || marksDTO.getCategory().equalsIgnoreCase("BC-A")){
            int start = 40;
            marksDTO.setTelugu(getRandomNumber(start,96));
            marksDTO.setHindi(getRandomNumber(start,89));
            marksDTO.setEnglish(getRandomNumber(start,96));
            marksDTO.setMaths(getRandomNumber(start,99));
            marksDTO.setScience(getRandomNumber(start,97));
            marksDTO.setSocial(getRandomNumber(start,96));
        }else{
            int start = 1;
            marksDTO.setTelugu(getRandomNumber(start,96));
            marksDTO.setHindi(getRandomNumber(start,89));
            marksDTO.setEnglish(getRandomNumber(start,96));
            marksDTO.setMaths(getRandomNumber(start,99));
            marksDTO.setScience(getRandomNumber(start,97));
            marksDTO.setSocial(getRandomNumber(start,96));
        }

        return marksDTO;
    }

    private int getRandomNumber(int min,int max) {
        Random random = new Random();
        //return random.nextInt(max + min) + min;
        return random.nextInt(max-min) + min;
    }

    @GetMapping(value = "/readSaleStudents")
    public StudentSaleDTO readStudentAndSales() {
        CompletableFuture<List<StudentDTO>> studentData =
                supplyAsync(() -> studentService.readStudentDetails());
        List<StudentDTO> student = null;
        try {
            student = studentData.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ResponseEntity<SalesOrderDTO[]> response =
                restTemplate.getForEntity(
                        "http://SALES-SERVICE/sales/readSales",
                        SalesOrderDTO[].class);
        SalesOrderDTO[] sales = response.getBody();

        SalesOrderDTO saleDto = restTemplate.getForObject("http://SALES-SERVICE/sales/readSale",
                SalesOrderDTO.class);
        System.out.println("Sales Dto===" + saleDto.toString());

        /*String str = restTemplate.getForObject("",String.class);
        return new ResponseEntity<String>(response,HttpStatus.OK);*/

        /*WebClient webClient = WebClient.create("http://SALES-SERVICE");
        List<SalesOrderDTO> sales = webClient.get()
                .uri("/sales/readSales")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SalesOrderDTO>>() {
                })
                .block();*/

        StudentSaleDTO dto = new StudentSaleDTO();
        dto.setStudentDTOS(student);
        //dto.setSalesOrderDTOS(new ArrayList<>(Arrays.asList(sales)));
        dto.setSalesOrderDTO(saleDto);
        return dto;
    }

}
