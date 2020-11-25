package course.spring.restmvc.model;

import lombok.*;

import java.time.LocalDateTime;
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
    private List<String> violations = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(@NonNull int code, @NonNull String message, List<String> violations) {
        this.code = code;
        this.message = message;
        this.violations = violations;
    }
}
