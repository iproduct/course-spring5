package course.spring.blogs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    UserService userService;

    @GetMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE)
    public String findUserByUsernamePath(@PathVariable String username) {
//        return String.format("<h1>Hello %s, from Spring REST Service, using @PathVariable!!!<h1>",
//                name);
        try {
            return mapper.writeValueAsString(userService.getUserByUsername(username));
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "{\"status\":500,\"message\":\"Error sending User JSON\"}", ex);
        }
    }

    @GetMapping
    public String sayHello(@RequestParam(value = "name", required = false) String name) {
        return String.format("<h1>Hello %s, from Spring REST Service!!!<h1>",
                name != null ? name : "Anonymous");
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public String getUserPost(
            @PathVariable(name = "userId") Long userId,
            @MatrixVariable(name="mode", pathVar = "userId") String userMode,
            @PathVariable(name = "postId") Long postId,
            @MatrixVariable(name="mode", pathVar = "postId") String postMode
            ) {
        return String.format("User %d in mode %s -> Post %d in mode %s",
                userId, userMode, postId, postMode);
    }

    @GetMapping("/accept-header")
    public String getAcceptHeader(@RequestHeader("Accept") String acceptHeader){
        return String.format("Accept: %s", acceptHeader);
    }

    @GetMapping(value = "/all-headers", produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getAllHeaders(@RequestHeader MultiValueMap<String, String> allHeaders){
        return allHeaders.toSingleValueMap();
    }

}
