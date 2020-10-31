package com.rkdevblog.karate.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.rkdevblog.karate.dto.AllEmployeeResponseDto;
import com.rkdevblog.karate.dto.EmployeeResponseDto;
import com.rkdevblog.karate.model.Employee;
import com.rkdevblog.karate.pdfthymeleaf.PDFThymeleafExample;
import com.rkdevblog.karate.service.TestExporter;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/download1")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        ServletContext context = request.getServletContext();
        PDFThymeleafExample thymeleaf2Pdf = new PDFThymeleafExample();
        String html = thymeleaf2Pdf.parseThymeleafTemplate();
        thymeleaf2Pdf.generatePdfFromHtml(html,request);
        String absolutePathToIndexJSP = context.getRealPath("/test2.pdf");
        File file = new File(absolutePathToIndexJSP);
        if(file.exists()){
            try{
                FileInputStream inputStream = new FileInputStream(file);
                response.setHeader("content-disposition", "inline; filename=test2.pdf");
                response.setContentType("application/pdf");
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[8192];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @GetMapping("/download2")
    public void exportToPDF(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";

        response.setHeader(headerKey, headerValue);

        TestExporter exporter = new TestExporter();
        exporter.export(response);

    }
//
//    @GetMapping("/download3")
//    public ResponseEntity<byte[]> getPDF1(){
//
//
//        File file = new File("C:\\Users\\prakr\\Documents\\GitHub\\1212345\\API-test-karate-example\\src\\main\\resources\\reports\\Resume_KPC.pdf");
//        if(file.exists()){
//            try{
//                HttpHeaders headers = new HttpHeaders();
//
//                headers.setContentType(MediaType.parseMediaType("application/pdf"));
//                String filename = "pdf1.pdf";
//
//                headers.add("content-disposition", "inline;filename=" + filename);
//
//                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//
//                FileInputStream inputStream = new FileInputStream(file);
//                OutputStream outputStream = response.getOutputStream();
//                byte[] buffer = new byte[8192];
//                int bytesRead = -1;
//                while((bytesRead = inputStream.read(buffer)) != -1){
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                inputStream.close();
//                outputStream.close();
//                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdf1Bytes, headers, HttpStatus.OK);
//                return response;
//                //file.delete();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
}
