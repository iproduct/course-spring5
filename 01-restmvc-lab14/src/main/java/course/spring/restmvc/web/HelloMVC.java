package course.spring.restmvc.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2")
public class HelloMVC {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        return String.format("Hello %s, from Spring Web MVC!", name);
    }

    @GetMapping({"/users/{userId}/posts/{postId}", "/users/{userId}/posts"})
    public String getUserPost(@PathVariable(name = "userId") Integer userId,
                              @MatrixVariable(name="mode", pathVar="userId") String userMode,
                              @PathVariable(name = "postId", required = false) Integer postId,
                              @MatrixVariable(name="mode", pathVar="postId") String postMode) {
        return String.format("User: %d in mode: %s -> Post: %d in mode: %s%n", userId, userMode, postId, postMode);
    }



}
