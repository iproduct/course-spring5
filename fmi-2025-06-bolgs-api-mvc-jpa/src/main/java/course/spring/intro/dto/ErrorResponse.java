package course.spring.intro.dto;

import java.util.List;

public record ErrorResponse(int status, String message, List<String> violations) {
}
