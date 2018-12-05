package org.iproduct.spring.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 3, max = 30)
    @NonNull
    private String username;

    @NotNull
    @Length(min = 5, max = 30)
    @NonNull
    private String password;

    @NotNull
//    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Email(message = "Email should be valid")
    @NonNull
    private String email;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String fname;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String lname;

    @NonNull
//    @CollectionTable
    private String role;

    private boolean active = true;

//    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")

    private Date created = new Date();
//    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private Date updated = new Date();

    public User(long id, @NotNull @Length(min = 3, max = 30) String username, @NotNull @Length(min = 5, max = 30) String password, @NotNull @Length(min = 1, max = 30) String fname, @NotNull @Length(min = 1, max = 30) String lname, String email, String role, boolean active, Date created, Date updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
        this.active = active;
        this.created = created;
        this.updated = updated;
    }

    public User() {
    }

//    @JsonCreator
//    @java.beans.ConstructorProperties({"username", "password", "fname", "lname", "roles"})
    public User(@NotNull @Length(min = 3, max = 30) String username, @Length(min = 5, max = 30) String password, @Length(min = 1, max = 30) String fname, @Length(min = 1, max = 30) String lname, String email, String roles) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = roles;
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
        return getAuthoritiesForRoles(getRole());
    }

    private Collection<GrantedAuthority> getAuthoritiesForRoles(String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        return authorities;
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
