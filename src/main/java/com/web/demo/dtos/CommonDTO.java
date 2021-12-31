package com.web.demo.dtos;

import java.util.List;

public class CommonDTO {

    private List<CropInsuranceDTO> cropDto;
    private List<SalesOrderDTO> salesDto;
    private List<StudentDTO> studentDto;

    public List<CropInsuranceDTO> getCropDto() {
        return cropDto;
    }

    public void setCropDto(List<CropInsuranceDTO> cropDto) {
        this.cropDto = cropDto;
    }

    public List<SalesOrderDTO> getSalesDto() {
        return salesDto;
    }

    public void setSalesDto(List<SalesOrderDTO> salesDto) {
        this.salesDto = salesDto;
    }

    public List<StudentDTO> getStudentDto() {
        return studentDto;
    }

    public void setStudentDto(List<StudentDTO> studentDto) {
        this.studentDto = studentDto;
    }
}
