package course.springdata.advanced.dao;

import course.springdata.advanced.entity.Ingredient;
import course.springdata.advanced.entity.Label;
import course.springdata.advanced.entity.Shampoo;
import course.springdata.advanced.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabelOrderByPriceDesc(Size medium, Label s);

    List<Shampoo> findByPriceGreaterThanEqual(double minPrice);

    List<Shampoo> findByPriceBetween(double minPrice, double maxPrice);

    int countShampoosByPriceLessThan(double maxPrice);

    @Query("SELECT s FROM Shampoo s, IN(s.ingredients) i WHERE i = :ingredient")
    List<Shampoo> findByIngredient(Ingredient ingredient);

   // @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :ingredient_names")
    @Query("SELECT s FROM Shampoo s, IN(s.ingredients) i WHERE i.name IN :ingredient_names")
    List<Shampoo> findWithIngredientsIn(@Param("ingredient_names") Iterable<String> ingredient_names);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size  < :count")
    List<Shampoo> findByCountOfIngredientsLowerThan(@Param("count") int maxCount);

}
