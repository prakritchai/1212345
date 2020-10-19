package com.rkdevblog.karate.dto;

import com.rkdevblog.karate.model.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllEmployeeResponseDto {

    private List<Employee> employees;
}
