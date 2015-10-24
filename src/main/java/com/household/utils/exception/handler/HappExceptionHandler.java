package com.household.utils.exception.handler;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.utils.exception.EntityValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.household.utils.ResponseEntityExceptionCreator.create;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * Created by artemvlasov on 04/10/15.
 */
@ControllerAdvice
public class HappExceptionHandler {
    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity validatorHandler(EntityValidationException ex) {
        if(ex.getObjectNode() != null) {
            return create(NOT_ACCEPTABLE, ex.getMessage(), ex.getObjectNode());
        } else {
            return create(NOT_ACCEPTABLE, ex.getMessage());
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        return ResponseEntity.status(FORBIDDEN).body(JsonNodeFactory.instance.objectNode().put("error", ex.getMessage()));
    }
}
