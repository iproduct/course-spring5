package course.hibernate.spring.dao;

import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepositoryDTO_JPQL extends JpaRepository<User, Long> {
    @Query("SELECT " +
            "NEW course.hibernate.spring.dto.UserDetailDto(u.id, u.firstName, u.lastName, u.username, GROUP_CONCAT(r)) " +
            "FROM User u LEFT JOIN u.roles r GROUP BY u.id")
    List<UserDetailDto> findAllUserDtos();
    Optional<User> findByUsername(String username);
}


















