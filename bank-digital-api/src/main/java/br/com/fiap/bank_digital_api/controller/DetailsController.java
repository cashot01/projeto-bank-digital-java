package br.com.fiap.bank_digital_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailsController {
    private static final String projectName = "Bank digital API";
    private static final String author1 = "Eduardo Henrique Strapazzon Nagado - RM 558158";
    private static final String author2 = "Cauan Passos - RM 555466";
    @GetMapping
    public String projectDetails (){
        return projectName + " - " + "Author: " + author1 + " - " + author2;
    }
}
