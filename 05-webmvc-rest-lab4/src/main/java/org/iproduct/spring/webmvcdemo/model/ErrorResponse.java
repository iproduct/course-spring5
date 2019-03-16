package org.iproduct.spring.webmvcdemo.model;

import lombok.*;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @NonNull
    private int code;
    @NonNull
    private String message;
    private List<FieldError> errors = new ArrayList<>();
}
