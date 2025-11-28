package demos.spring.vehicles.web;

import demos.spring.vehicles.model.Brand;
import demos.spring.vehicles.model.EngineType;
import demos.spring.vehicles.model.Offer;
import demos.spring.vehicles.model.User;
import demos.spring.vehicles.service.BrandService;
import demos.spring.vehicles.service.Favourites;
import demos.spring.vehicles.service.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
@SessionAttributes("favs")
@Slf4j
public class OfferController {
    public static  final String UPLOAD_DIR = "uploads";
    private BrandService brandService;
    private OfferService offerService;
    private Favourites favourites;

    @Autowired
    public OfferController(BrandService brandService, OfferService offerService, Favourites favs) {
        this.brandService = brandService;
        this.offerService = offerService;
        this.favourites = favs;
    }

    @ModelAttribute("favs")
    public Collection<Offer> addAttributes(Model model) {
        return favourites.getAllOffers();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

    }

    @GetMapping
    public String getBrands(Model brands) {
        brands.addAttribute("offers", offerService.getOffers()); // set model data
        return "offers";
    }

    @GetMapping("/add")
    public String getOfferForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            redirectAttributes.addAttribute("redirectUrl", "/offers/add");
            return "redirect:/auth/login";
        }
        if (!model.containsAttribute("offer")) {
            model.addAttribute("offer", new Offer());
        }
        model.addAttribute("brands", brandService.getBrands());
        return "offer-add";
    }

    @PostMapping("/add")
    public String createNewOffer(@Valid @ModelAttribute("offer") Offer offer, final BindingResult binding,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request, HttpSession session,
                                 @RequestParam(name = "file", required = false) MultipartFile file) {
//        request.getParameterMap().entrySet().stream().map(e -> Arrays.asList(e.getValue())).forEach(System.out::println);
        Enumeration<String> attrs = session.getAttributeNames();
        while( attrs.hasMoreElements()) {
            System.out.println(attrs.nextElement());
        }
        System.out.println(session.getAttributeNames().asIterator());
        if (binding.hasErrors()) {
            List<String> errorMessages = binding.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Error in '%s' - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).collect(Collectors.toList());
            log.error("Error creating offer: {}", errorMessages);
//            model.addAttribute("fileError", null);
            return redirectToAddOfferFormGet(offer, binding, null, redirectAttributes);
        }

        try {
            offer.setSeller((User) session.getAttribute("user"));
            if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0) {
                if (Pattern.matches(".+\\.(jpg|png|webp)", file.getOriginalFilename())) {
                    handleMultipartFile(file);
                    offer.setImageUrl("/" + UPLOAD_DIR + "/" + file.getOriginalFilename());
                } else {
//                    model.addAttribute("fileError", "Submit picture [.jpg, .png]");
                    return redirectToAddOfferFormGet(offer, binding, "Submit picture [.jpg, .png, .webp]", redirectAttributes);
                }
            }
            log.info("POST Article: " + offer);
            offerService.createOffer(offer);
        } catch (Exception ex) {
            log.error("Error creating offer", ex);
            return redirectToAddOfferFormGet(offer, binding, ex.getMessage(), redirectAttributes);

        }

        return "redirect:/offers";
    }

    @GetMapping("/details/{offerId:\\d+}")
    public String getOfferDetails(@PathVariable("offerId") Long offerId, Model model) {
        model.addAttribute("offer", offerService.getOfferById(offerId));
        return "offer-details";
    }

    @GetMapping("/favs/add/{offerId}")
    public String addFavourite(@PathVariable("offerId") Long offerId, Model model) {
        Offer offer = offerService.getOfferById(offerId);
        favourites.addOffer(offer);
        model.addAttribute("offer", offer);
        return "offer-details";
    }

    @GetMapping("/favs/remove/{offerId}")
    public String removeFavourite(@PathVariable("offerId") Long offerId) {
        favourites.removeOfferById(offerId);
        return "favs";
    }

    @GetMapping("/favs")
    public String getFavourites() {
        return "favs";
    }

    // private utility methods
    private String redirectToAddOfferFormGet(Offer offer,
                                             BindingResult binding,
                                             String errors,
                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessages", errors);
        redirectAttributes.addFlashAttribute("offer", offer);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer", binding);
        return "redirect:add";
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOAD_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
