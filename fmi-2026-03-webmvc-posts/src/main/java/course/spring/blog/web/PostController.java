package course.spring.blog.web;

import course.spring.blog.entity.Post;
import course.spring.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/posts")
@Log
public class PostController {
    public static final String UPLOADS_DIR = "tmp";
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView getPosts() {
        var modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postService.getAllPosts());
        return modelAndView;
    }

    @PostMapping
    public String actionsPost(@RequestParam(value = "edit", required = false) Long edit,
                              @RequestParam(value = "delete", required = false) Long delete,
                              UriComponentsBuilder uriComponentsBuilder,
                              RedirectAttributes redirectAttributes
    ) {
        log.info("actions: Post edit: " + edit + ", delete: " + delete);
        if (edit != null) {
//            redirectAttributes.addAttribute("mode", "edit");
//            redirectAttributes.addAttribute("editId", edit);
//            return "redirect:/post-form";
            URI uri = uriComponentsBuilder.path("/posts/add")
                    .query("mode=edit&postId={id}").buildAndExpand(edit).toUri();
            return "redirect:" + uri.toString();
        }
        if (delete != null) {
            postService.deletePostById(delete);
        }
        return "redirect:/posts";
    }

    @GetMapping("/add")
    public String getPostForm(@ModelAttribute("post") Post article,
                              @RequestParam(value = "mode", required = false) String mode,
                              @RequestParam(value = "postId", required = false) Long articleId,
                              Model model) {
        log.info("show Post form: " + article);
        if ("edit".equals(mode)) {
            model.addAttribute("post", postService.getPostById(articleId));
        }

        model.addAttribute("path", "/posts/add");
        model.addAttribute("title", "Add Post");

        return "post-form";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("post") Post post, Errors errors,
                          @RequestParam("file") MultipartFile file, Model model) {
        if (errors.hasErrors()) {
            log.warning("Invalid post data: " + errors.toString());
            return "post-form";
        }
        if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
            var imgFilenamePattern = Pattern.compile(".+\\.(jpe?g|png)");
            var matcher = imgFilenamePattern.matcher(file.getOriginalFilename());
            if (matcher.matches()) {
                var ext = matcher.group(1);
                UUID uuid = UUID.randomUUID();
                var filename = uuid.toString() + "." + ext;
                log.info(filename);
                if (handleMultipartFile(file, filename)) {
                    post.setImageUrl("uploads/" + filename);
                } else {
                    model.addAttribute("fileError", "Error saving image: " + file.getOriginalFilename());
                    return "post-form";
                }
            } else {
                model.addAttribute("fileError", "Submit picture in [.jpeg | .png] format");
                return "post-form";
            }
        }
        if (post.getId() == null) {  // create
            log.info("Create new post:" + post);
            postService.addPost(post);
        } else {
            log.info("UPDATE post: " + post);
            postService.updatePost(post);
        }
        return "redirect:/posts";
    }

    private boolean handleMultipartFile(MultipartFile file, String filename) {
        long size = file.getSize();
        log.info("File: " + filename + ", size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + filename;
            var imageFile = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(imageFile));
            return true;
        } catch (IOException e) {
            log.severe("Error saving image file: " + e.getMessage());
            return false;
        }

    }

}
