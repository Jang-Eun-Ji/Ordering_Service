package org.spring.Ordering.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommonResponseDto {
    private HttpStatus status;
    private String message;
    private Object result;
}
