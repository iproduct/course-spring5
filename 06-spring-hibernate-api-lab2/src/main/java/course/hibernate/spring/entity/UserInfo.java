package course.hibernate.spring.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserInfo {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @ToString.Exclude
    @NonNull
    private User user;
    
    @NonNull
    private String qualifications;
}
