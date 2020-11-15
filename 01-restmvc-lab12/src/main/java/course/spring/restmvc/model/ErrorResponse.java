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
}
