package course.spring.restmvc.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    int status;
    @NonNull
    String message;
    List<String> violations = new ArrayList<>();
    LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(int status, @NonNull String message, List<String> violations) {
        this.status = status;
        this.message = message;
        this.violations = violations;
    }
}
