package course.spring.blogs.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class ErrorResponse {
    @NonNull
    private Integer status;
    @NonNull
    private String message;
    List<String> violations = new ArrayList<>();
    LocalDateTime timestamp = LocalDateTime.now();
}
