package course.spring.restmvc.web;

import org.springframework.web.bind.annotation.*;


@RestController
public class HelloMVC {
    @GetMapping("/hello")
    public String sayHello(
            @RequestParam(name = "name", required = false, defaultValue = "Stranger") String name){
        return String.format("Hello %s, from Spring MVC!", name);
    }

    @GetMapping({"/hello/{name}/{family}", "/hello/{name}"})
    public String sayHelloPath(
            @PathVariable("name") String name,
            @PathVariable(value = "family", required = false) String family){
        return String.format("Hello %s %s, from Spring MVC, using Path Variable!", name, family);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public String getUserPost(
            @PathVariable long userId,
            @MatrixVariable(name = "mode", pathVar = "userId", defaultValue = "present") String userMode,
            @PathVariable long postId,
            @MatrixVariable(name = "mode", pathVar = "postId", defaultValue = "present") String postMode){
        return String.format("User: %d in mode: %s, Post: %d in mode: %s.", userId, userMode, postId, postMode);
    }




}
