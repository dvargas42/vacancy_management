package br.com.vargas.vacancy_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageResponse {
    
    private String message;
    private String field;
}
