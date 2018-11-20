package br.com.ractecnologia.springbootessentials.handler;


import br.com.ractecnologia.springbootessentials.error.ErrorDetails;
import br.com.ractecnologia.springbootessentials.error.ResourceNotFoundDetails;
import br.com.ractecnologia.springbootessentials.error.ResourceNotFoundException;
import br.com.ractecnologia.springbootessentials.error.ValidationErrorDetails;
import com.sun.istack.internal.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice //para usar a camada RestExceptionHandler atraves das camadas do spring
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ResourceNotFoundDetails rfnDetails = ResourceNotFoundDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource Not found")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();

        return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);


    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
        fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails rfnDetails = ValidationErrorDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(manvException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();

        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);


    }



@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.Builder
            .newBuilder()
            .timestamp(new Date().getTime())
            .status(HttpStatus.NOT_FOUND.value())
            .title("Internal Exception")
            .detail(ex.getMessage())
            .developerMessage(ex.getClass().getName())
            .build();

        return new ResponseEntity<>(errorDetails, headers, status);
    }
}
