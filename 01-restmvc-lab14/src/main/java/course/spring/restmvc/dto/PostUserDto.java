package course.spring.restmvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostUserDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String firstName;
    private String lastName;

}
