package org.iproduct.spring.restmvc.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Document(collection="roles")
@Data
@Builder
public class Role {

    public static final String ROLE_USER ="ROLE_USER";
    public static final String ROLE_ADMIN ="ROLE_ADMIN";

    @Id
    private String id;

    @NotNull
    @NonNull
    @Length(min=2, max=30)
    private String name;

    private Collection<Permission> permissions;

    @java.beans.ConstructorProperties({"name", "permissions"})
    public Role(@NotNull @Length(min = 2, max = 30) String name, Collection<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public Role(@NotNull @Length(min = 2, max = 30) String name) {
        this.name = name;
    }
}
