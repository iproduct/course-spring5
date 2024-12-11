package course.hibernate.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {
    @NotNull
    @Positive
    @NonNull
    private Long id;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String firstName;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String lastName;
    @NotNull
    @Size(min=5, max=20)
    @NonNull
    private String username;
}
