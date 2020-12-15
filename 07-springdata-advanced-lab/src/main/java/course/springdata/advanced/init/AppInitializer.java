package course.springdata.advanced.init;

import course.springdata.advanced.dao.IngredientRepository;
import course.springdata.advanced.dao.LabelRepository;
import course.springdata.advanced.dao.ShampooRepository;
import course.springdata.advanced.entity.Ingredient;
import course.springdata.advanced.entity.Shampoo;
import course.springdata.advanced.util.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static course.springdata.advanced.entity.Size.MEDIUM;

@Component
public class AppInitializer implements CommandLineRunner {
    private final ShampooRepository shampooRepo;
    private final LabelRepository labelRepo;
    private final IngredientRepository ingredientRepo;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepo, LabelRepository labelRepo, IngredientRepository ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.labelRepo = labelRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void run(String... args) throws Exception {
//        // Fetch Shampoos by Size
//        shampooRepo.findBySizeOrderById(MEDIUM).forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");
//
//        // Fetch Shampoos by Size
//        shampooRepo.findBySizeOrLabelOrderByPriceDesc(MEDIUM, labelRepo.findByTitle("Vital").get())
//                .forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");
//
//        // Fetch Shampoos by Price greater than or equal
//        shampooRepo.findByPriceGreaterThanEqual(7)
//                .forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");
//
//        // Fetch Shampoos by Price between min and max
//        shampooRepo.findByPriceBetween(5, 8).forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");

//        // Select Ingredients by Names
//        ingredientRepo.findByNameIn(Set.of("Lavender", "Herbs", "Apple")).forEach(PrintUtil::printIngredient);
//        System.out.println("-".repeat(80) + "\n");

//        // Fetch Shampoos by Price between min and max
//        double maxPrice = 8.50;
//        System.out.printf("Number of shampoos with price less than %5.2f is: %d",
//                maxPrice,
//                shampooRepo.countShampoosByPriceLessThan(maxPrice));

//        // Fetch Shampoos by Ingredients in list
//        shampooRepo.findWithIngredientsIn(Set.of("Berry", "Mineral-Collagen")).forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");

//        // Fetch Shampoos by Ingredients in list
//        shampooRepo.findByCountOfIngredientsLowerThan(3).forEach(PrintUtil::printShampoo);
//        System.out.println("-".repeat(170) + "\n");

//        // Delete ingredients by name
//        String nameToDelete = "Macadamia Oil";
//        Ingredient ingredientToDelete = ingredientRepo.findByName(nameToDelete).get();
//        List<Shampoo> shampoosByIngredient = shampooRepo.findByIngredient(ingredientToDelete);
//        shampoosByIngredient.forEach(PrintUtil::printShampoo);
//        shampoosByIngredient.forEach(shampoo -> shampoo.getIngredients().remove(ingredientToDelete));
//        System.out.printf("Number of deleted ingredients with name='%s' is: %d",
//                nameToDelete, ingredientRepo.deleteAllByName(nameToDelete));
//        System.out.println("-".repeat(180) + "\n");
//        shampooRepo.findAll().forEach(PrintUtil::printShampoo);

        // Increase price of ingredinets in list by percentage
        ingredientRepo.findAll().forEach(PrintUtil::printIngredient);
        System.out.println("-".repeat(80) + "\n");

        System.out.printf("Number of ingredients updated: %d\n\nAfter update:\n",
            ingredientRepo.updatePriceOfIngredientsInList(Set.of("Lavender", "Herbs", "Apple"), 1.2));

        ingredientRepo.findAll().forEach(PrintUtil::printIngredient);
        System.out.println("-".repeat(80) + "\n");
    }
}
