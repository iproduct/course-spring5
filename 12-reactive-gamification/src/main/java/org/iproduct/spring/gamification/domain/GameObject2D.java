package org.iproduct.spring.gamification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameObject2D {
    @NonNull
    private long id;
    @NonNull
    private String name;
    @NonNull
    private double positionX;
    @NonNull
    private double positionY;
    private double velocityX = 0;
    private double velocityY = 0;
}
