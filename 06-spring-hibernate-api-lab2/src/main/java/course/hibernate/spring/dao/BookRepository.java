package course.hibernate.spring.dao;

import course.hibernate.spring.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
