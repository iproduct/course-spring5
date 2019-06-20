package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{

}
