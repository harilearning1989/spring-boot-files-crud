package com.web.demo.dtos;

import java.util.List;

public class StudentSaleDTO {

    private List<StudentDTO> studentDTOS;
    private List<SalesOrderDTO> salesOrderDTOS;
    private SalesOrderDTO salesOrderDTO;

    public SalesOrderDTO getSalesOrderDTO() {
        return salesOrderDTO;
    }

    public void setSalesOrderDTO(SalesOrderDTO salesOrderDTO) {
        this.salesOrderDTO = salesOrderDTO;
    }

    public List<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }

    public void setStudentDTOS(List<StudentDTO> studentDTOS) {
        this.studentDTOS = studentDTOS;
    }

    public List<SalesOrderDTO> getSalesOrderDTOS() {
        return salesOrderDTOS;
    }

    public void setSalesOrderDTOS(List<SalesOrderDTO> salesOrderDTOS) {
        this.salesOrderDTOS = salesOrderDTOS;
    }
}
