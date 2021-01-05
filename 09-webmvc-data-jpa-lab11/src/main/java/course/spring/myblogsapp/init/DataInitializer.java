package course.spring.myblogsapp.init;

import course.spring.myblogsapp.dao.CompanyRepository;
import course.spring.myblogsapp.dao.ProjectRepository;
import course.spring.myblogsapp.dao.RoleRepository;
import course.spring.myblogsapp.dao.UserRepository;
import course.spring.myblogsapp.entity.Company;
import course.spring.myblogsapp.entity.Post;
import course.spring.myblogsapp.entity.Project;
import course.spring.myblogsapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {
    public static final List<Post> posts = List.of(
            new Post("New in Spring 5", "WebFlux is here and is haigh performace...",
                    "https://p2.piqsels.com/preview/639/504/10/branch-engine-leaves-spring.jpg",
                    "admin", Set.of("Spring", "WebFlux")),
            new Post("DI in Spring", "Many ways to do it, but what are advantages ...",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRGq5nnPNS-nVHQWbwmKNtxhVs7hZkTD_VuYA&usqp=CAU",
                    "admin", Set.of("Spring", "dependency injection", "DI")),
            new Post("Autowiring", "To autowire or not to autowire ...",
                    "https://www.publicdomainpictures.net/pictures/280000/nahled/electrical-wiring.jpg",
                    "admin", Set.of("Spring", "Autowire"))
    );

    public static final List<Company> companies = List.of(
            new Company("ABC Ltd"),
            new Company("SU FMI"),
            new Company("Best Widgets Ltd"),
            new Company("Software AD")
    );

    public static final List<Project> projects = List.of(
            new Project("Build new Message Server", "Build a message server implementing JMS API.", 70000, companies.get(3)),
            new Project("Build eLearning Platform", "Build new eLearning platform for FMI.", 40000, companies.get(1)),
            new Project("Build 3D menus", "Build 3D menus for a new game interface.", 40000, companies.get(2))
    );

    @Autowired
    private PostService postService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(postService.getPostsCount() == 0) {
            posts.forEach(postService::addPost);
        }
        companies.forEach(companyRepository::save);
        List<Project> created = projects.stream().map(projectRepository::save).collect(Collectors.toList());
        created.forEach(proj -> proj.getCompany().getProjects().add(proj)); // !!! TODO: second direction -> move to service

        companyRepository.findAll().forEach(System.out::println);
        System.out.println();
        projectRepository.findAll().forEach(System.out::println);
    }
}
