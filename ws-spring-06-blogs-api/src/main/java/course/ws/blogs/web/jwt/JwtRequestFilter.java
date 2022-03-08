package course.ws.blogs.web.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.ws.blogs.dto.ErrorResponse;
import course.ws.blogs.entity.User;
import course.ws.blogs.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;
            if (authorizationHeader != null) {
                if (authorizationHeader.startsWith("Bearer ")) {
                    jwtToken = authorizationHeader.substring(7);
                    try {
                        username = jwtUtils.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException ex) {
                        log.error("Unable to get JWT token.");
                        throw new BadCredentialsException("Unable to get JWT token.");
                    } catch (ExpiredJwtException ex) {
                        log.error("JWT token has expired.");
                        throw new BadCredentialsException("JWT token has expired.");
                    }
                } else {
                    log.error("JWT token does not begin with 'Bearer ' prefix.");
                    throw new BadCredentialsException("WT token does not begin with 'Bearer ' prefix.");
                }
            }

            if (username != null) {
                User user = userService.getByUsername(username);
                if (jwtUtils.validateToken(jwtToken, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new BadCredentialsException("JWT token has expired.");
                }
            }
            filterChain.doFilter(request, response);
        } catch(BadCredentialsException ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            var writer = response.getWriter();
            writer.write(mapper.writeValueAsString(
                    new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())
            ));
        }
    }
}
