package com.example.festapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validaciones (@NotBlank, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<> ();

        ex.getBindingResult().getAllErrors().forEach(error -> {
           if (error instanceof FieldError fieldError) {
                // Errores de campos (@NotBlank, @Size, etc.)
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                // Errores de clase (@PasswordMatches)
                errors.put("passwordConfirm", error.getDefaultMessage());
            }
        });

        return ResponseEntity.badRequest().body(errors);
    }

    // Usuario ya existe
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserExists(
        UserAlreadyExistsException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Usuario invalido
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUser(
        InvalidCredentialsException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(401).body(error);
        
    }
}
