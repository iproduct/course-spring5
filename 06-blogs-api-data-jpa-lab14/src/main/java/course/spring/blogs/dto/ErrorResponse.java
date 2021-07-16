package course.spring.blogs.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    private Integer status;
    @NonNull
    private String message;
    private List<String> violations = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(@NonNull Integer status, @NonNull String message, List<String> violations) {
        this.status = status;
        this.message = message;
        this.violations = violations;
    }
}
