package course.spring.intro.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResposnse {
    @NonNull
    Integer code;
    @NonNull
    String message;
    List<String> violations = new ArrayList<>();
    LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResposnse(@NonNull Integer code, @NonNull String message, List<String> violations) {
        this.code = code;
        this.message = message;
        this.violations = violations;
    }
}
