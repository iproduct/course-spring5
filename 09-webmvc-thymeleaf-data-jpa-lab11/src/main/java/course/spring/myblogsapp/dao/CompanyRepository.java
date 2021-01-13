package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
