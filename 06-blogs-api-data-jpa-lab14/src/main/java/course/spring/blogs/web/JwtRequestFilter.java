package course.spring.blogs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dto.ErrorResponse;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.UserService;
import course.spring.blogs.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private UserRepository userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ErrorHandlerControllerAdvice errorHandler;
    @Autowired
    ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if(authorizationHeader != null) {
            try {
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
            } catch (AuthenticationException e) {
                ResponseEntity<ErrorResponse> respEntity = errorHandler.handleAuthenticationException(e);
                response.setStatus(respEntity.getStatusCodeValue());
                response.getWriter().write(objectMapper.writeValueAsString(respEntity.getBody()));
                return;
            }
        }

        if(username != null) {
            User user = userRepo.findByUsername(username).orElseThrow(
                    ()-> new NonexistingEntityException("User with does not exist"));
            if(jwtUtils.validateToken(jwtToken, user)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
