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

    @PreAuthorize("hasAuthority('add-productField')")
    @PostMapping("/add-product-field")
    public ResponseEntity<ProductFieldDto> createProductField(@RequestBody ProductFieldDto productFieldDto) {
        return ResponseEntity.ok(productFieldService.save(productFieldDto));
    }

    @PreAuthorize("hasAuthority('getAll-productField')")
    @GetMapping("/product-field-all")
    public ResponseEntity<List<ProductFieldDto>> getAllProductField() {
        List<ProductFieldDto> productFieldDtoList = productFieldService.getAll();
        return ResponseEntity.ok(productFieldDtoList);
    }

    @PreAuthorize("hasAuthority('getById-productField')")
    @GetMapping("/product-field/{id}")
    public ResponseEntity<ProductFieldDto> getProductFieldById(@PathVariable Long id) {
        ProductFieldDto productFieldDto = productFieldService.findById(id);
        return ResponseEntity.ok(productFieldDto);
    }

    @PreAuthorize("hasAuthority('getByName-productField')")
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductFieldDto> getProductFieldByName(@PathVariable String name) {
        ProductFieldDto productFieldDto = productFieldService.findByName(name);
        return ResponseEntity.ok(productFieldDto);
    }

    @PreAuthorize("hasAuthority('getByName-productField')")
    @GetMapping("/names/{name}")
    public ResponseEntity<List<ProductFieldDto>> getAllProductFieldsByName(@PathVariable String name) {
        List<ProductFieldDto> productProcessDtoList = productFieldService.searchByName(name);
        return ResponseEntity.ok(productProcessDtoList);
    }

    @GetMapping("/{id}/product-field-value")
    public ResponseEntity<List<ProductFieldDto>> getProductFieldByProductFieldValueId(@PathVariable Long id) {
        List<ProductFieldDto> productFieldDtoList = productFieldService.getProductFieldByProductFieldValueId(id);
        return ResponseEntity.ok(productFieldDtoList);
    }


    @PreAuthorize("hasAuthority('delete-productField')")
    @DeleteMapping("/delete-product-field/{id}")
    public ResponseEntity<String> deleteProductField(@PathVariable Long id) {
        productFieldService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('delete-productFieldValues')")
    @DeleteMapping("/delete-product-field-value/{id}/{pfvId}")
    public ResponseEntity<String> deleteProductionFieldValues(@PathVariable Long id, @PathVariable Long pfvId) {
        productFieldService.deleteProductFieldValuesById(id, pfvId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('update-productField')")
    @PutMapping("/update-product-field/{id}")
    public ResponseEntity<ProductFieldDto> updateProductField(@PathVariable Long id, @RequestBody ProductField productField) {
        ProductFieldDto updatedPfDto = productFieldService.updatedProductField(id, productField);
        return ResponseEntity.ok(updatedPfDto);
    }
}
