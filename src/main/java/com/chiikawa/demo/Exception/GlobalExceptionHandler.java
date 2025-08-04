package com.chiikawa.demo.Exception;

import com.chiikawa.demo.Exception.model.DuplicateResourceException;
import com.chiikawa.demo.Exception.model.ResourceNotFoundException;
import com.chiikawa.demo.model.BaseResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseModel> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponseModel("fail.", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<BaseResponseModel> handleDuplicateException (DuplicateResourceException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponseModel("fail.", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseModel> handleGenericError(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponseModel("fail.","something went wrong! " + ex.getMessage()));
    }
}
