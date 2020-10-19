package com.rkdevblog.karate.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.rkdevblog.karate.dto.AllEmployeeResponseDto;
import com.rkdevblog.karate.dto.EmployeeResponseDto;
import com.rkdevblog.karate.model.Employee;
import com.rkdevblog.karate.service.TestExporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setName(employee.getName());
        employeeResponseDto.setMessage("Successfully created employee");

        //Generator testPdf = new Generator();
        //employeeResponseDto.setPdf(testPdf.parseThymeleafTemplate());
        return ResponseEntity.ok(employeeResponseDto);
    }

    @GetMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int id) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setName("john");
        employeeResponseDto.setMessage("Successfully returned employee");
        if (id != 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeeResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllEmployees() {

        Employee employee = new Employee();
        employee.setId("emp-1");
        employee.setName("john");
        employee.setAddress("winterfell");
        employee.setEmail("john@gmail.com");

        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            employees.add(employee);
        }

        AllEmployeeResponseDto allEmployeeResponseDto = new AllEmployeeResponseDto();
        allEmployeeResponseDto.setEmployees(employees);

        return new ResponseEntity<>(allEmployeeResponseDto, HttpStatus.OK);
    }

    @GetMapping("/export1")
    public void createPdf(HttpServletRequest request, HttpServletResponse response){
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/resources/reports/Resume_KPC.pdf");
        File file = new File(path);
        if(file.exists()){
            try{
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(path);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename=Resume_KPC.pdf");
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                //file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @GetMapping("/export2")
    public void exportToPDF(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";

        response.setHeader(headerKey, headerValue);

        TestExporter exporter = new TestExporter();
        exporter.export(response);

    }
}
