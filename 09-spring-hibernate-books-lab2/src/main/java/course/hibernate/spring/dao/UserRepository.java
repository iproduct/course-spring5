package course.hibernate.spring.dao;

import course.hibernate.spring.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<User> findAll();
    Optional<User> findByUsername(String username);
}


















