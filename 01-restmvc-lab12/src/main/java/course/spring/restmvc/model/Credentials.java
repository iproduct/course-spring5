package course.spring.restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    @NotNull
    @Size(min=2, max=30)
    private String username;
    @NotNull
    @Size(min=4, max=30)
    private String password;
}
