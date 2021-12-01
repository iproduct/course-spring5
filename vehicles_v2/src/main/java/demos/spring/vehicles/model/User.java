package demos.spring.vehicles.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Length(min = 2, max = 20)
    private String firstName;

    @NonNull
    @Length(min = 2, max = 20)
    private String lastName;

    @NonNull
    @Length(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    @NotNull
    @EqualsAndHashCode.Include
    private String username;

    @NonNull
    @Length(min = 4, max = 20)
    @Column(nullable = false)
    @NotNull
    private String password;

    @NonNull
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    private boolean active = true;

    @Length(min = 8, max = 512)
    private String imageUrl;

    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Offer> offers = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified = new Date();

    public User(@Length(min = 2, max = 60) String firstName,
                @Length(min = 2, max = 60) String lastName,
                @Length(min = 3, max = 60) @NotNull String username,
                @Length(min = 4, max = 80) @NotNull String password,
                @NotEmpty Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = "/img/user-avatar.svg";
    }

    public User(@Length(min = 2, max = 60) String firstName,
                @Length(min = 2, max = 60) String lastName,
                @Length(min = 3, max = 60) @NotNull String username,
                @Length(min = 4, max = 80) @NotNull String password,
                @NotEmpty Set<Role> roles,
                @Length(min = 8, max = 512) String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> role.toString() + "_ROLE")
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return active;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return active;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return active;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return active;
//    }

}
