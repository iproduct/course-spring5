package course.spring.restjpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    Integer status;
    @NonNull
    String message;
    List<String> violations = new ArrayList<>();
    LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(int status, String message, List<String> violations) {
        this.status = status;
        this.message = message;
        this.violations = violations;
    }
}
