package course.spring.restmvc.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @GetMapping("/hello-headers")
    public String getRequestHeaders(@RequestHeader("Accept") String accept){
        return String.format("Access: %s", accept);
    }

    @GetMapping(path="/hello-all-headers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequestHeaders(HttpServletRequest request){
        Map<String, String> headers = new LinkedHashMap<>();
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> {
                    StringBuilder values = new StringBuilder();
                    request.getHeaders(headerName).asIterator()
                            .forEachRemaining(val -> values.append(val).append(", "));
                    headers.put(headerName, values.toString());
                });
        return headers;
    }



}
