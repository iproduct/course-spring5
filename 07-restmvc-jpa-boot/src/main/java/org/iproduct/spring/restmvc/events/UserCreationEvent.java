package org.iproduct.spring.restmvc.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.iproduct.spring.restmvc.model.User;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UserCreationEvent {
        private final User user;
}
