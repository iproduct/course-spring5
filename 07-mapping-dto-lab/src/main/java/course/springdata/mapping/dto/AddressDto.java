package course.springdata.mapping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AddressDto {
    private Long id;
    private String country;
    private String city;
    private String details;
}
