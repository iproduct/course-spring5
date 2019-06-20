package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Format;

public interface FormatRepository extends JpaRepository<Format, Integer>{

}
