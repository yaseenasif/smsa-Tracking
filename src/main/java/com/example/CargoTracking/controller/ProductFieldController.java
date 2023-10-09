package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.ProductFieldDto;
import com.example.CargoTracking.model.ProductField;
import com.example.CargoTracking.service.ProductFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductFieldController {
    private final ProductFieldService productFieldService;

    public ProductFieldController(ProductFieldService productFieldService) {
        this.productFieldService = productFieldService;
    }

    @PostMapping("/add-product-field")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldDto> createProductField(@RequestBody ProductFieldDto productFieldDto) {
        return ResponseEntity.ok(productFieldService.save(productFieldDto));
    }

    @GetMapping("/product-field-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductFieldDto>> getAllProductField() {
        List<ProductFieldDto> productFieldDtoList = productFieldService.getAll();
        return ResponseEntity.ok(productFieldDtoList);
    }

    @GetMapping("/product-field/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldDto> getProductFieldById(@PathVariable Long id) {
        ProductFieldDto productFieldDto = productFieldService.findById(id);
        return ResponseEntity.ok(productFieldDto);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldDto> getProductFieldByName(@PathVariable String name) {
        ProductFieldDto productFieldDto = productFieldService.findByName(name);
        return ResponseEntity.ok(productFieldDto);
    }

    @GetMapping("/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductFieldDto>> getAllProductFieldsByName(@PathVariable String name) {
        List<ProductFieldDto> productProcessDtoList = productFieldService.searchByName(name);
        return ResponseEntity.ok(productProcessDtoList);
    }

    @GetMapping("/{id}/product-field-value")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductFieldDto>> getProductFieldByProductFieldValueId(@PathVariable Long id) {
        List<ProductFieldDto> productFieldDtoList = productFieldService.getProductFieldByProductFieldValueId(id);
        return ResponseEntity.ok(productFieldDtoList);
    }


    @DeleteMapping("/delete-product-field/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProductField(@PathVariable Long id) {
        productFieldService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-product-field-value/{id}/{pfvId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProductionFieldValues(@PathVariable Long id, @PathVariable Long pfvId) {
        productFieldService.deleteProductFieldValuesById(id, pfvId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-product-field/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldDto> updateProductField(@PathVariable Long id, @RequestBody ProductField productField) {
        ProductFieldDto updatedPfDto = productFieldService.updatedProductField(id, productField);
        return ResponseEntity.ok(updatedPfDto);
    }
}
