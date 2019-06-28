package org.iproduct.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.UserService;
import org.iproduct.spring.restmvc.web.resource.UserResource;
import org.iproduct.spring.restmvc.web.resource.UserResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@ExposesResourceFor(UserResource.class)
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserResourceAssembler assembler;

    @GetMapping
    public Resources<List<UserResource>> getUsers() {
        List<User> users = service.getUsers();
        List<UserResource> userResources = assembler.toResources(service.getUsers());
        List<Link> links = new ArrayList<>();
        if(users.size() > 0) {
            links.add(assembler.linkToSingleResource(users.get(0)).withRel("first"));
            links.add(assembler.linkToSingleResource(users.get(0)).withRel("last"));
        }
        return new Resources(userResources, links);
    }

    @GetMapping("{id}")
    public UserResource getUserById(@PathVariable String id) {
        return assembler.toResource(service.getUserById(id));
    }

    @DeleteMapping("{id}")
    public User deleteUsers(@PathVariable String id) {
        return service.deleteUser(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = service.createUser(user);
        URI location = MvcUriComponentsBuilder.fromMethodName(UserController.class, "createUser", User.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = uriComponentsBuilder.path(req.getServletPath())
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        log.info("User created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> addUser(@PathVariable String id, @RequestBody User user) {
        if(!user.getId().equals(id)) throw new InvalidEntityIdException(
                String.format("User ID=%s from path is different from Entity ID=%s", id, user.getId()));
        User updated = service.updateUser(user);
        log.info("User updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

}
