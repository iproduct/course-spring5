package org.iproduct.ml.h2o.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hex.genmodel.easy.exception.PredictException;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.ml.h2o.domain.Quote;
import org.iproduct.ml.h2o.domain.RecognitionResult;
import org.iproduct.ml.h2o.domain.Resource;
import org.iproduct.ml.h2o.services.DnnRecognizerService;
import org.iproduct.ml.h2o.services.ExtractEmbeddingsService;
import org.iproduct.ml.h2o.services.QuotesGenerator;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class ReactiveWebSocketHandler implements WebSocketHandler {
    @Autowired
    private QuotesGenerator generator;

    @Autowired
    private ExtractEmbeddingsService embeddingsService;

    @Autowired
    private DnnRecognizerService recognizerService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        Flux<RecognitionResult> recognitions = webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(text -> {
                    try {
                        return mapper.readValue(text, Resource.class);
                    } catch (JsonProcessingException e) {
                        log.error("Error reading value from JSON:", e);
                        throw new RuntimeException(e);
                    }
                })
                .map(resource -> embeddingsService.extractEmbeddings(resource))
                .filter(resource -> resource != null)
                .map(embeddings -> {
                    try {
                        RecognitionResult result = recognizerService.recognize(embeddings.getEmbeddings());
                        System.out.printf("Recognition result: %s%n", result);
                        return result;
                    } catch (PredictException e) {
                        log.error("Error predicting embedding: ", e);
                        throw new RuntimeException(e);
                    }
                })
                .onErrorReturn(new RecognitionResult())
                .log().share();

        Mono<Void> output = webSocketSession.send(recognitions
//                .map(RecognitionResult::toString)
                .map(result -> {
                    try {
                        return mapper.writeValueAsString(result);
                    } catch (JsonProcessingException e) {
                        log.error("Error reading value from JSON:", e);
                        throw new RuntimeException(e);
                    }
                })
                .map(webSocketSession::textMessage));

        return output;
    }


//    public Mono<Void> handle(WebSocketSession webSocketSession) {
//        return webSocketSession.send(Flux.interval(Duration.ofMillis(1000))
//                .map(n -> n+ "")
//                .map(webSocketSession::textMessage))
//                .and(webSocketSession.receive()
//                        .map(WebSocketMessage::getPayloadAsText)
//                        .log());
//    }

//    public Mono<Void> handle(WebSocketSession webSocketSession) {
//        return webSocketSession.send(
//                generator.getQuoteStream(Duration.ofMillis(5000))
//                .map(obj -> {
//                    try {
//                        return mapper.writeValueAsString(obj);
//                    } catch (JsonProcessingException e) {
//                        log.error("Error mapping value to JSON:", e);
//                        throw new RuntimeException(e);
//                    }
//                })
//                .map(n -> n + "")
//                .map(webSocketSession::textMessage))
//                .and(webSocketSession.receive()
//                        .map(WebSocketMessage::getPayloadAsText)
//                        .log());
//    }

}

