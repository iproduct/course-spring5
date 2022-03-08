package course.ws.blogs.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @NonNull
    private int status;
    @NonNull
    private String message;
    private List<String> violations = List.of();
}
