package org.iproduct.spring.restmvc.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Document(collection = "permissions")
@Data
@RequiredArgsConstructor
public class Permission {
    public static final String OWN = "OWN";
    public static final String ALL = "ALL";
    public static final String READ = "READ";
    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    @Id
    private String id;

    @NotNull
    @NonNull
    private String owner;

    @NotNull
    @NonNull
    private String asset;

    @NotNull
    @NonNull
    private String operation;

    @Override
    public String toString() {
        return this.getOwner() + "_" + this.getAsset() + "_" + this.getOperation();
    }

}
