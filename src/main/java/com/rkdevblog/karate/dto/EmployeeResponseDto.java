package com.rkdevblog.karate.dto;

import com.rkdevblog.karate.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.xhtmlrenderer.pdf.PDFCreationListener;

import java.util.List;

@Getter
@Setter
public class EmployeeResponseDto {

    private String name;
    private String message;
    private String pdf;
}
