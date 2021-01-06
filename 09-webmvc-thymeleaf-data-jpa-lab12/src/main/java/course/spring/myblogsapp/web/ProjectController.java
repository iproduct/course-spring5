package course.spring.myblogsapp.web;

import course.spring.myblogsapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String getPosts(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("path", "projects");
        return "projects";
    }
}
