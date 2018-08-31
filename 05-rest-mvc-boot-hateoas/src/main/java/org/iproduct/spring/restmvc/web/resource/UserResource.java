package org.iproduct.spring.restmvc.web.resource;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.iproduct.spring.restmvc.model.Role;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Relation(value="author", collectionRelation="authors")
@JsonPropertyOrder({ "id", "username", "fname", "lname", "active", "roles", "created", "updated"})
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class UserResource extends ResourceWithEmbeddeds {

    @JsonProperty(value="id")
    private String userId;

    private String username;
    private String fname;
    private String lname;
    private List<Role> roles;
    private boolean active = true;
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    @JsonCreator
    public UserResource(String id, String username, String fname, String lname, List<Role> roles, boolean active, LocalDateTime created, LocalDateTime updated) {
        this.userId = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.active = active;
        this.created = created;
        this.updated = updated;
    }
}
