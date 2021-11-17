package course.spring.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErorResposnse {
    private final int status;
    private final String message;
    private String details;
}
