package course.spring.myblogsapp.service;

import course.spring.myblogsapp.dao.ProjectRepository;
import course.spring.myblogsapp.entity.Project;
import course.spring.myblogsapp.exception.NonExisitingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Validated
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepo;

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepo.findById(id).orElseThrow(
                () -> new NonExisitingEntityException(
                        String.format("Project with ID='%d' does not exists", id)));
    }

    @Override
    @Transactional
    public Project addProject(@Valid Project project) {
        project.setId(null);
        return projectRepo.save(project);
    }

    @Override
    @Transactional
    public Project updateProject(@Valid Project project) {
        Project old = getProjectById(project.getId());
        return projectRepo.save(project);
    }

    @Override
    @Transactional
    public Project deleteProject(Long id) {
        Project deleted = getProjectById(id);
        projectRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getProjectsCount() {
        return projectRepo.count();
    }
}
