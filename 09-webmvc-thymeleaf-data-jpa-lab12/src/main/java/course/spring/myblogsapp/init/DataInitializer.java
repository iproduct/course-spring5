package course.spring.myblogsapp.init;

import course.spring.myblogsapp.dao.CompanyRepository;
import course.spring.myblogsapp.entity.Company;
import course.spring.myblogsapp.entity.Project;
import course.spring.myblogsapp.entity.Role;
import course.spring.myblogsapp.entity.User;
import course.spring.myblogsapp.service.ProjectService;
import course.spring.myblogsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    public static final List<Company> companies = List.of(
            new Company("ABC Ltd."),
            new Company("Sofia University"),
            new Company("Best Widgets Ltd."),
            new Company("Software AD")
    );

    public static final List<Project> projects = List.of(
            new Project("Build CI/CD Server", "Build custom continuous integration server for our projects ...",
                    70000, companies.get(3)),
            new Project("Create Furniture Web Site", "Build web site for our client selling furniture ...",
                    20000, companies.get(0)),
            new Project("Update SUSI with eLearning Functionality", "Add eLearning functionality to SUSI ...",
                    50000, companies.get(2)),
            new Project("Build IoT Control Access System", "Build custom IoT system controlling acces to to FMI building ...",
                    70000, companies.get(2))
    );

    public static final List<User> users = List.of(
            new User("Default", "Admin", "admin@gmail.com", "admin", "admin",
                    Set.of(Role.ADMIN)),
            new User("Ivan", "Petrov", "ivan@gmail.com", "ivan", "ivan123"),
            new User("Veronika", "Dimitrova", "vera@gmail.com", "veronika", "veronika",
                    Set.of(Role.EMPLOYEE, Role.MANAGER, Role.ADMIN))
    );

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(companyRepository.count() == 0) {
            companyRepository.saveAll(companies);
//            companies.forEach(companyRepository::save);
            companyRepository.findAll().forEach(System.out::println);
        }
        if(projectService.getProjectsCount() == 0) {
            projects.forEach(projectService::addProject);
            projects.forEach(p -> p.getCompany().getProjects().add(p));
            projectService.getAllProjects().forEach(System.out::println);
        }
        if(userService.getUsersCount() == 0) {
            users.forEach(userService::addUser);
            projects.get(0).getUsers().addAll(users);
            users.forEach(u -> u.getProjects().add( projects.get(0)));
            userService.getAllUsers().forEach(System.out::println);
        }
    }
}
