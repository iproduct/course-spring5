package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.exception.InvalidEntityDataException;
import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.ErrorResponse;
import io.netty.handler.codec.http.HttpResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@Order(-2)
@Slf4j
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    @Autowired
    GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                   ResourceProperties resourceProperties, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(
            ErrorAttributes errorAttributes) {

        return RouterFunctions.route(
                RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Throwable error = getError(req);
        if(error instanceof NonexisitngEntityException) {
            return ServerResponse.status(NOT_FOUND)
                .syncBody(new ErrorResponse(NOT_FOUND, getError(req).getMessage()));
        } else {
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .syncBody(new ErrorResponse(BAD_REQUEST, getError(req).getMessage()));
        }

//        Map<String, Object> errorPropertiesMap = getErrorAttributes(req, false);


    }
}
