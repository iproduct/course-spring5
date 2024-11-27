package demos.spring.vehicles.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class VehiclesControllerAdvice {
    @ModelAttribute("servletPath")
    public String servletPath(final HttpServletRequest request) {
        return request.getServletPath();
    }
}
