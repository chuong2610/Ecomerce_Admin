package org.group5.ecomerceadmin.controller;

import jakarta.validation.Valid;
import org.group5.ecomerceadmin.entity.Brand;
import org.group5.ecomerceadmin.payload.request.BrandRequest;
import org.group5.ecomerceadmin.payload.response.BrandResponse;
import org.group5.ecomerceadmin.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAll() {
        List<BrandResponse> responses = brandService.getAll().stream().map(brandService::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getById(@PathVariable String id) {
        return brandService.getById(id)
                .map(brandService::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BrandResponse> create(@Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandService.createFromRequest(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(@PathVariable String id, @Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandService.updateFromRequest(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
