package demos.spring.vehicles.service.impl;

import demos.spring.vehicles.dao.BrandRepository;
import demos.spring.vehicles.dao.UserRepository;
import demos.spring.vehicles.exception.EntityNotFoundException;
import demos.spring.vehicles.model.Brand;
import demos.spring.vehicles.model.Model;
import demos.spring.vehicles.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private UserRepository userRepo;

//    @Autowired
//    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Collection<Brand> getBrands() {
        return brandRepo.findAll(Sort.by("name"));
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Brand with ID=%s not found.", id)));
    }

    @Override
    public Brand createBrand(@Valid Brand brand) {
        if (brand.getCreated() == null) {
            brand.setCreated(new Date());
        }
        brand.setModified(brand.getCreated());

        return brandRepo.save(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        brand.setModified(new Date());
        Brand old = getBrandById(brand.getId());
        if (old == null) {
            throw new EntityNotFoundException(String.format("Brand with ID=%s not found.", brand.getId()));
        }
        return brandRepo.save(brand);
    }

    @Override
    public Brand deleteBrand(Long id) {
        Brand old = getBrandById(id);
        brandRepo.deleteById(id);
        return old;
    }

    @Override
    public long getBrandsCount() {
        return brandRepo.count();
    }

    // Declarative transaction
    @Transactional
    public List<Brand> createBrandsBatch(List<Brand> brands) {
        List<Brand> created = brands.stream()
                .map(brand -> {
                    Brand resultBrand = createBrand(brand);
                    return resultBrand;
                }).collect(Collectors.toList());
        return created;
    }

    @Override
    public Model getModelById(Long id) {
        return brandRepo.findModelById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Model with ID=%s not found.", id)));
    }

}
