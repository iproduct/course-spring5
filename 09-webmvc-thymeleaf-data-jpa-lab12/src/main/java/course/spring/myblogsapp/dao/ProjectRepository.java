package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Company;
import course.spring.myblogsapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
