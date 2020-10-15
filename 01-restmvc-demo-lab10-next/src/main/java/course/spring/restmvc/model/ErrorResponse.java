package course.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @NonNull
    Integer code;
    @NonNull
    String message;
    List<String> constraintViolations = new ArrayList<>();
    List<String> exceptionMessages = new ArrayList<>();
}
