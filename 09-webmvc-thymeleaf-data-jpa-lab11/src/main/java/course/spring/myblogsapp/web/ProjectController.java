package course.spring.myblogsapp.web;

import course.spring.myblogsapp.entity.Project;
import course.spring.myblogsapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String getProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("path", "projects");
        return "projects";
    }
    @GetMapping("project-form")
    public String getProjectForm(Model model) {
        if(model.getAttribute("project") == null) {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("path", "projects/project-form");
        return "project-form";
    }

    @PostMapping("project-form")
    public String submitProjectForm(@Valid @ModelAttribute("project") Project project, BindingResult binding,
        RedirectAttributes redirectAttr) {
        if(binding.hasErrors()) {
            redirectAttr.addFlashAttribute("project", project);
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.project", binding);
            return "redirect:project-form";
        }
        projectService.addProject(project);
        return "redirect:/projects";
    }
}
