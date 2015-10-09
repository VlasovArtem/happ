package com.household.utils.exception.handler;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by artemvlasov on 04/10/15.
 */
@ControllerAdvice
public class HappExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        return ResponseEntity.status(FORBIDDEN).body(JsonNodeFactory.instance.objectNode().put("error", ex.getMessage()));
    }
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity PreviousPaymentExceptionHandler(Exception ex) {
//        System.out.println("Exception");
//        if("No value present".equals(ex.getMessage())) {
//            return ResponseEntity.status(OK).build();
//        }
//        return ResponseEntity
//                .status(FORBIDDEN)
//                .body(JsonNodeFactory.instance.objectNode().put("error", ex.getMessage()));
//    }
}
