package br.com.vargas.vacancy_management.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageResponse>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageResponse> response = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErrorMessageResponse responseError = new ErrorMessageResponse(message, error.getField());
            response.add(responseError);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<String> handlerUserNotFoundException(CandidateNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handlerAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CompanyNotFountException.class)
    public ResponseEntity<String> handlerCompanyNotFoundException(CompanyNotFountException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNullException.class)
    public ResponseEntity<String> handlerPasswordNullException(PasswordNullException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailFoundException.class)
    public ResponseEntity<String> handlerEmailFoundException(EmailFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsernameFoundException.class)
    public ResponseEntity<String> handlerUsernameFoundException(UsernameFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyFoundException.class)
    public ResponseEntity<String> handlerUserFoundException(CompanyFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Object> handlerException(Exception e) {
    //     return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    // }
}
