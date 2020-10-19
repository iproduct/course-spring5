package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.ValidationErrorsException;
import course.spring.restmvc.model.dto.UserDto;
import course.spring.restmvc.model.entity.User;
import course.spring.restmvc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable long id) {
        return convertToDto(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) {
        User created = userService.createUser(user);
        return ResponseEntity.created(
//                ServletUriComponentsBuilder.fromRequest(request).pathSegment("{id}").build(created.getId())
//                UriComponentsBuilder.fromUriString(request.getRequestURL().toString()).pathSegment("{id}").build(created.getId())
//                MvcUriComponentsBuilder.fromMethodName(UsersResource.class, "createUser", user, request)
//                        .pathSegment("{id}").build(created.getId())
                MvcUriComponentsBuilder.fromMethodCall(on(UserResource.class).createUser(user, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        if(errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, user.getId()));
        }
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleInvalidEntityData(InvalidEntityDataException ex) {
//        return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleNonexisitingEntity(NonexistingEntityException ex) {
//        return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
//    }

    private UserDto convertToDto(User user){
        UserDto userDto = mapper.map(user, UserDto.class);
        //... setOtherProps
        return userDto;
    }

    private User convertToEntity(UserDto userDto){
        User user = mapper.map(userDto, User.class);
        //...
        return user;
    }

}
