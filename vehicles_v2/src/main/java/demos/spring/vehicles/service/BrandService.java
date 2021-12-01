package demos.spring.vehicles.service;

import demos.spring.vehicles.model.Brand;
import demos.spring.vehicles.model.Model;

import java.util.Collection;
import java.util.List;

public interface BrandService {
    Collection<Brand> getBrands();
    Brand getBrandById(Long id);
    Brand createBrand(Brand post);
    Brand updateBrand(Brand post);
    Brand deleteBrand(Long id);
    long getBrandsCount();
    List<Brand> createBrandsBatch(List<Brand> posts);
    Model getModelById(Long id);
}
