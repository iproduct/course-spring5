package org.iproduct.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document(collection = "users")
@JsonIgnoreProperties(value = {"authorities", "name",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
@Data
@Builder
public class User implements UserDetails {

    @Id
    private String id;

    @NotNull
    @Length(min = 3, max = 30)
    @NonNull
    private String username;

    @NotNull
    @Length(min = 5, max = 30)
    @NonNull
    private String password;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String fname;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String lname;

    @NonNull
    private List<Role> roles;

    @Builder.Default
    private boolean active = true;

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    public User(String id, @NotNull @Length(min = 3, max = 30) String username, @NotNull @Length(min = 5, max = 30) String password, @NotNull @Length(min = 1, max = 30) String fname, @NotNull @Length(min = 1, max = 30) String lname, List<Role> roles, boolean active, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.active = active;
        this.created = created;
        this.updated = updated;
    }

    public User() {
    }

    @java.beans.ConstructorProperties({"username", "password", "fname", "lname", "roles"})
    public User(@NotNull @Length(min = 3, max = 30) String username, @Length(min = 5, max = 30) String password, @Length(min = 1, max = 30) String fname, @Length(min = 1, max = 30) String lname, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
    }


//    @Override
//    public String getPassword() {
//        if(password.charAt(0) == '{')
//            return password;
//        else
//            return "{noop}"+ password;
//    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesForRoles(getRoles());
    }

    private Collection<GrantedAuthority> getAuthoritiesForRoles(List<Role> roles) {
        return roles.stream()
                .flatMap(role ->
                    Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getName())),
                        role.getPermissions().stream().map(perm -> new SimpleGrantedAuthority(perm.toString()))
                    )
                ).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
