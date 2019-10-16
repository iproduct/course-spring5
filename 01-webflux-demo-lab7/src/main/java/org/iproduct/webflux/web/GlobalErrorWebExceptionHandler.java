package org.iproduct.webflux.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.webflux.exception.NonexisitngEntityException;
import org.iproduct.webflux.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    @Autowired
    GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                   ResourceProperties resourceProperties, ApplicationContext applicationContext,
                                   ServerCodecConfigurer configurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(
            ErrorAttributes errorAttributes) {

        return route(path("/api/articles/**"), this::renderErrorResponse);
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
    }
}
