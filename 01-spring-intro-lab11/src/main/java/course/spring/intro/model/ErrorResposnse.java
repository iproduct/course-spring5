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
    LocalDateTime timestamp = LocalDateTime.now();
    List<String> violations = new ArrayList<>();
}
