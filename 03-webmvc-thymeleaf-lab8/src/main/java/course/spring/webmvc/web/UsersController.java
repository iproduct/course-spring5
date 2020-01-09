package course.spring.webmvc.web;

import course.spring.webmvc.domain.UsersService;
import course.spring.webmvc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
@Slf4j
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Value("${uploads.directory}")
    private String uploadsDir;

    @GetMapping
    public String getUsers(Model model, Authentication auth) {
        model.addAttribute("path", "users");
        model.addAttribute("users", usersService.findAll());
        log.debug("GET Users: {}",  usersService.findAll());
        return "users";
    }

    @PostMapping(params = "edit")
    public String editUser(@RequestParam("edit") String editId, Model model, UriComponentsBuilder uriBuilder){
        log.info("Editing user: " + editId);
        URI uri = uriBuilder.path("/users/user-form")
                .query("mode=edit&userId={id}").buildAndExpand(editId).toUri();
        return "redirect:" + uri.toString();
    }

    @PostMapping(params = "delete")
    public String deleteUser(@RequestParam("delete") String deleteId, Model model){
        log.info("Deleting user: " + deleteId);
        User old = usersService.remove(deleteId);
        handleFile(null, old);
        return "redirect:/users";
    }


    @GetMapping("/user-form")
    public ModelAndView getUserForm(
            @ModelAttribute("user") User user,
            @RequestParam(value="mode", required=false) String mode,
            @RequestParam(value="userId", required=false) String userId
    ) {
        ModelAndView result = new ModelAndView("user-form");
        String title = "Add New User";
        if("edit".equals(mode)) {
            User found = usersService.findById(userId);
            result.addObject("user", found);
            title = "Edit User";
        }

        result.addObject("title", title);
        result.addObject("path", MvcUriComponentsBuilder.fromMethodName(
                UsersController.class,"getUserForm", new User(), "", "")
                    .build().getPath());
        return result;
    }

    @PostMapping("/user-form")
    public String addUser(
            @Valid @ModelAttribute ("user") User user,
            BindingResult errors,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model,
            @NotNull
            Authentication auth) {
        User loggedIn = (User)auth.getPrincipal();
        model.addAttribute("fileError", null);
        if(file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, user);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "user-form";
            }
        }
        if(loggedIn == null) {
            errors.addError(new ObjectError("user", "No authenticated user"));
            return "user-form";
        }
        if(errors.hasErrors()
            && !(user.getId() != null && "".equals(user.getPassword()) && errors.getFieldErrorCount()==1 && errors.getFieldError("password") != null)) { // password error in edit mode is ignored
            return "user-form";
        }
        if(user.getId() == null) {  // create
            log.info("Create new user: {}", user);
            usersService.add(user);
        } else { //edit
            log.info("Edit user: {}", user);
            usersService.update(user);
        }
        return "redirect:/users";
    }

    private void handleFile(MultipartFile file, User user) {
        String oldName = user.getPictureUrl();
        if(oldName != null && oldName.length() > 0) { //delete old image file
            Path oldPath = Paths.get(getUploadsDir(), oldName).toAbsolutePath();
            log.info("Deleting path: {}", oldPath);
            if (Files.exists(oldPath)) {
                try {
                    Files.delete(oldPath);
                } catch (IOException ex) {
                    log.error("Error deleting file: {}", oldPath);
                }
            }
        }
        if(file != null && file.getOriginalFilename().length() > 4) {
            String newName = file.getOriginalFilename();
            Path newPath = Paths.get(getUploadsDir(), newName).toAbsolutePath();
            int n = 0;
            String finalName = newName;
            while (Files.exists(newPath)) {   // change destination file name if it already exists
                finalName = newName.substring(0, newName.length()-4) + "_" + ++n + newName.substring(newName.length()-4);
                newPath = Paths.get(getUploadsDir(), finalName).toAbsolutePath();
            }
            try {
                log.info("Saving new file: '{}', size: {}", newPath, file.getSize());
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newPath));
                user.setPictureUrl(finalName);
            } catch (IOException ex) {
                log.error("Error coping file: {} [{}]", newPath, file.getSize());
            }
        }
    }

    protected String getUploadsDir() {
        File uploadsDir = new File(this.uploadsDir);
        if(!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        return uploadsDir.getAbsolutePath();
    }

}
