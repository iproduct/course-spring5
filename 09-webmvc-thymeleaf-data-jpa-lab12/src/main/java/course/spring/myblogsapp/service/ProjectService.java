package course.spring.myblogsapp.service;

import course.spring.myblogsapp.entity.Project;

import javax.validation.Valid;
import java.util.List;

public interface ProjectService {
    List<Project>  getAllProjects();
    Project  getProjectById(Long id);
    Project addProject(@Valid Project project);
    Project updateProject(@Valid Project project);
    Project deleteProject(Long id);
    long getProjectsCount();
}
