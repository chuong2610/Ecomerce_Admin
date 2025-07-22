package org.group5.ecomerceadmin.service;

import jakarta.transaction.Transactional;
import org.group5.ecomerceadmin.entity.Brand;
import org.group5.ecomerceadmin.payload.request.BrandUpdateRequest;
import org.group5.ecomerceadmin.repository.BrandRepository;
import org.group5.ecomerceadmin.payload.request.BrandRequest;
import org.group5.ecomerceadmin.payload.response.BrandResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getById(String id) {
        return brandRepository.findById(id);
    }

    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> searchByName(String name) {
        return brandRepository.findByNameContainingIgnoreCase(name);
    }


    public Brand update(String id, Brand brand) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + id));
        existing.setName(brand.getName());
        existing.setDescription(brand.getDescription());
        return brandRepository.save(existing);
    }

    public void delete(String id) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + id));
        brandRepository.delete(existing);
    }

    public BrandResponse toResponse(Brand brand) {
        BrandResponse resp = new BrandResponse();
        resp.setId(brand.getId());
        resp.setName(brand.getName());
        resp.setDescription(brand.getDescription());
        return resp;
    }

    public BrandResponse createFromRequest(BrandRequest request) {
        Brand brand = new Brand();
        brand.setId(request.getId());
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        Brand saved = create(brand);
        return toResponse(saved);
    }

    public BrandResponse updateFromRequest(String id, BrandUpdateRequest request) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        Brand updated = update(id, brand);
        return toResponse(updated);
    }
}
