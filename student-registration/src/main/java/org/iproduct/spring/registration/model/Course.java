package org.iproduct.spring.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 3, max = 80)
    @NonNull
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private Date startDate = new Date();

    @ManyToOne
    private User instructor;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String fname;

    @NotNull
    @NonNull
    @Length(min = 1, max = 30)
    private String lname;

    @NonNull
    private String roles;

    private boolean active = true;

//    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated = new Date();

    public Course(long id, @NotNull @Length(min = 3, max = 30) String username, @NotNull @Length(min = 5, max = 30) String password, @NotNull @Length(min = 1, max = 30) String fname, @NotNull @Length(min = 1, max = 30) String lname, String email, String roles, boolean active, Date created, Date updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.roles = roles;
        this.active = active;
        this.created = created;
        this.updated = updated;
    }

    public Course() {
    }

//    @JsonCreator
//    @java.beans.ConstructorProperties({"username", "password", "fname", "lname", "roles"})
    public Course(@NotNull @Length(min = 3, max = 30) String username, @Length(min = 5, max = 30) String password, @Length(min = 1, max = 30) String fname, @Length(min = 1, max = 30) String lname, String email, String roles) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
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

    private Collection<GrantedAuthority> getAuthoritiesForRoles(String roles) {
        return Arrays.asList(roles.split("\\s*,\\s*")).stream()
                .map(String::trim)
                .filter(role -> role.length() > 0)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
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
