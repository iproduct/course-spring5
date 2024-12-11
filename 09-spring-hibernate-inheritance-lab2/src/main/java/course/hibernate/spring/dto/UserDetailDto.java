package course.hibernate.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import course.hibernate.spring.entity.EntityBase;
import course.hibernate.spring.entity.Role;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class UserDetailDto {
    @NotNull
    private Long id;
    @NotNull
    @Size(min=2, max =20)
    private String firstName;
    @NotNull
    @Size(min=2, max =20)
    private String lastName;
    @NotNull
    @Size(min=5, max =20)
    private String username;
    private String roles;

    public UserDetailDto() {
    }

    public UserDetailDto(Long id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public UserDetailDto(Long id, String firstName, String lastName, String username, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                "}";
    }
}
