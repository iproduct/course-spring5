package org.iproduct.spring.gamification.services;

import org.iproduct.spring.gamification.domain.GameConstraints;
import org.iproduct.spring.gamification.domain.GameObject2D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Component
public class ThorPositionsGenerator {

    @Autowired
    private GameConstraints gameConstraints;

    private Random random = new Random();

    //    private List<GameObject2D> gameObjects = Arrays.asList(
//            new GameObject2D(1L, "Thor", 200, 200)
//    );
    private GameObject2D thor = new GameObject2D(1L, "Thor", 50, 50, 30, 30);


    public Flux<GameObject2D> getGameObjectsStream(Duration period) {
        return Flux.interval(period)
//                .take(10)
                .scan(thor, (old, index) -> {
                    double x = old.getPositionX() + old.getVelocityX() * period.toMillis() / 1000D;
                    double y = old.getPositionY() + old.getVelocityY() * period.toMillis() / 1000D;
                    double vx = old.getVelocityX();
                    double vy = old.getVelocityY();
                    if (x > gameConstraints.getCanvasWidth() || x < 0) {
                        vx = -vx;
                    }
                    if (y > gameConstraints.getCanvasHeight() || y < 0) {
                        vy = -vy;
                    }
                    return new GameObject2D(old.getId(), old.getName(), x, y, vx, vy);
                })
                .share()
                .log();
    }
}
