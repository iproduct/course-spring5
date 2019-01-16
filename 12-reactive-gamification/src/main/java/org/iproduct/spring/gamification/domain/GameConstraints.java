package org.iproduct.spring.gamification.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class GameConstraints {
    private int canvasWidth = 800;
    private int canvasHeight = 500;
}
