package org.spring.Ordering.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseDto {
    public static ResponseEntity<Map<String, Object>> MakeMessage(HttpStatus httpStatus, String s){
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(httpStatus.value()));
        body.put("status message", s);
        body.put("error message", httpStatus.getReasonPhrase());
        return new ResponseEntity<>(body, httpStatus);
    }
}
