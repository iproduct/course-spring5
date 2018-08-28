package org.iproduct.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.model.Permission;
import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.service.PermissionService;
import org.iproduct.spring.restmvc.service.RoleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.iproduct.spring.restmvc.model.Permission.*;
import static org.iproduct.spring.restmvc.model.Role.ROLE_ADMIN;
import static org.iproduct.spring.restmvc.model.Role.ROLE_USER;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private ArticleService articles;

    @Autowired
    private UserService users;

    @Autowired
    private RoleService roles;

    @Autowired
    private PermissionService permissions;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Strting data initialization ...");
        Permission ownArticleRead = permissions.createPermissionIfNotExist(new Permission(OWN, "ARTICLE", READ));
        Permission ownArticleUpdate = permissions.createPermissionIfNotExist(new Permission(OWN, "ARTICLE", UPDATE));
        Permission ownArticleDelete = permissions.createPermissionIfNotExist(new Permission(OWN, "ARTICLE", DELETE));
        Permission articlesRead = permissions.createPermissionIfNotExist(new Permission(ALL, "ARTICLE", READ));
        Permission articlesCreate = permissions.createPermissionIfNotExist(new Permission(ALL, "ARTICLE", CREATE));
        Permission articlesUpdate = permissions.createPermissionIfNotExist(new Permission(ALL, "ARTICLE", UPDATE));
        Permission articlesDelete = permissions.createPermissionIfNotExist(new Permission(ALL, "ARTICLE", UPDATE));
        Permission ownUserRead = permissions.createPermissionIfNotExist(new Permission(OWN, "USER", READ));
        Permission ownUserUpdate = permissions.createPermissionIfNotExist(new Permission(OWN, "USER", UPDATE));
        Permission usersRead = permissions.createPermissionIfNotExist(new Permission(ALL, "USER", READ));
        Permission usersCreate = permissions.createPermissionIfNotExist(new Permission(ALL, "USER", CREATE));
        Permission usersUpdate = permissions.createPermissionIfNotExist(new Permission(ALL, "USER", UPDATE));
        Permission usersDelete = permissions.createPermissionIfNotExist(new Permission(ALL, "USER", DELETE));
        Role roleUser = roles.createRoleIfNotExist(new Role(ROLE_USER, Arrays.asList(new Permission[] {
                ownArticleRead, ownArticleUpdate, ownArticleDelete, articlesRead, articlesCreate, ownUserRead, ownUserUpdate
        } )));
        Role roleAdmin = roles.createRoleIfNotExist(new Role(ROLE_ADMIN, Arrays.asList(new Permission[] {
                ownArticleRead, ownArticleUpdate, ownArticleDelete,
                articlesRead, articlesCreate, articlesUpdate, articlesDelete,
                ownUserRead, ownUserUpdate,
                usersRead, usersCreate, usersUpdate, usersDelete
        } )));

        users.createUserIfNotExist(new User("admin", "admin", "DEFAULT", "ADMIN",
                Arrays.asList(new Role[]{ roleAdmin })));

    }
}
