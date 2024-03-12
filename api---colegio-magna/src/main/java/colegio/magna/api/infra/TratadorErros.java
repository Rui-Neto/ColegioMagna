package colegio.magna.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity tratarErro404() {
            return ResponseEntity.notFound().build();
        }
        
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

}