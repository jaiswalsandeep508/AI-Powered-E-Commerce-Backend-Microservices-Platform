package com.ecommerce.controller;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.dto.response.BrandResponse;
import com.ecommerce.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        return new ResponseEntity<>(brandService.createBrand(request), HttpStatus.CREATED);
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long brandId,@Valid @RequestBody BrandRequest request) {
        return new ResponseEntity<>(brandService.updateBrand(brandId, request),HttpStatus.OK);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>("Deleted brand successfully",HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long brandId) {
        return new ResponseEntity<>(brandService.getBrandById(brandId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return new ResponseEntity<>(brandService.getAllBrands(),HttpStatus.OK);
    }
}