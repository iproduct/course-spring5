package course.hibernate.spring.web;

import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.dto.mapping.UserDtoMapper;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private UserDtoMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserDtoMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<UserDetailDto> getAllUsers() {
        return userService.findAll(); //.stream()
//                .map(user -> modelMapper.map(user, UserDetailDto.class))
//                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    UserDetailDto getUserById(@PathVariable("id") Long id) {
        return modelMapper.mapUserToUserDetailDto(userService.findById(id));
    }
}
