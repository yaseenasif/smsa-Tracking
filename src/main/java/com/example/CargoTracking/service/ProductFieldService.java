package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.ProductFieldDto;
import com.example.CargoTracking.dto.ProductFieldValuesDto;
import com.example.CargoTracking.dto.Type;
import com.example.CargoTracking.model.ProductField;
import com.example.CargoTracking.model.ProductFieldValues;
import com.example.CargoTracking.repository.ProductFieldRepository;
import com.example.CargoTracking.repository.ProductFieldValuesRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductFieldService {

    private final ProductFieldRepository productFieldRepository;
    private final ProductFieldValuesRepository productFieldValuesRepository;

    public ProductFieldService(ProductFieldRepository productFieldRepository, ProductFieldValuesRepository productFieldValuesRepository){

        this.productFieldRepository = productFieldRepository;
        this.productFieldValuesRepository = productFieldValuesRepository;
    }

    @Transactional
    public ProductFieldDto save(ProductFieldDto productFieldDto) {
        if(productFieldDto.getCreatedAt() == null) {
            productFieldDto.setCreatedAt(LocalDate.now());
        }
        if(productFieldDto.getStatus() == null){
            productFieldDto.setStatus("Active");
        }

        ProductField productField = toEntity(productFieldDto);
        List<ProductFieldValues> productFieldValues = productField.getProductFieldValuesList();
        for (ProductFieldValues productFieldValue : productFieldValues) {
            productFieldValue.setProductField(productField);
            productFieldValue.setStatus("Active");
        }
        productField.setProductFieldValuesList(productFieldValues);
        ProductField createdProductField = productFieldRepository.save(productField);

        return toDto(createdProductField);
    }


    public List<ProductFieldDto> getAll() {
        List<ProductField> productFieldList = productFieldRepository.findAllByStatus("Active");
        List<ProductFieldDto> productFieldDtoList = new ArrayList<>();

        for (ProductField productField : productFieldList) {
            ProductFieldDto productFieldDto = toDto(productField);
            productFieldDtoList.add(productFieldDto);
        }
        return productFieldDtoList;
    }

    public ProductFieldDto findById(Long id){
        Optional<ProductField> optionalProductField = productFieldRepository.findById(id);

        if(optionalProductField.isPresent()) {
            ProductField productField = optionalProductField.get();
            return toDto(productField);
        }
        else {
            throw new RuntimeException(String.format("Product Field not found for id => %d", id));
        }
    }

    public ProductFieldDto findByName(String name) {
        Optional<ProductField> productFieldOptional = Optional.ofNullable(productFieldRepository.findByName(name));

        if(productFieldOptional.isPresent()){
            ProductField productField = productFieldOptional.get();
            return toDto(productField);
        }
        else {
            throw new RuntimeException(String.format("ProductField not found at => %s", name));
        }
    }

    public List<ProductFieldDto> searchByName(String name) {
        List<ProductField> productFieldList = productFieldRepository.findProductFieldsByName(name);
        List<ProductFieldDto> productFieldDtoList = new ArrayList<>();

        for (ProductField productField : productFieldList) {
            ProductFieldDto productFieldDto = toDto(productField);
            productFieldDtoList.add(productFieldDto);
        }
        return productFieldDtoList;
    }

    public List<ProductFieldDto> getProductFieldByProductFieldValueId(Long productFieldValueId) {
        Optional<List<ProductField>> optionalProductFieldList = Optional.ofNullable(productFieldRepository.findByProductFieldValuesList_Id(productFieldValueId));
        if(optionalProductFieldList.isPresent()){
            List<ProductField> productFieldList = optionalProductFieldList.get();
            List<ProductFieldDto> productFieldDtoList = new ArrayList<>();

            for (ProductField productField : productFieldList) {
                ProductFieldDto productFieldDto = toDto(productField);
                productFieldDtoList.add(productFieldDto);
            }
            return productFieldDtoList;
        } else{
            throw new RuntimeException(String.format("ProductField not found on Product Field Value id => %d", productFieldValueId));
        }
    }

    @Transactional
    public String deleteById(Long id) {
        Optional<ProductField> optionalProductField = productFieldRepository.findById(id);

        if(optionalProductField.isPresent()) {
            ProductField productField = optionalProductField.get();
//            productFieldRepository.setStatusInactive(id);
            productFieldRepository.deleteById(id);
        }
        else {
            throw new RuntimeException(String.format("Product Field not found for id => %d", id));
        }
        return null;
    }

    @Transactional
    public ProductFieldDto updatedProductField(Long id, ProductField productField) {
        Optional<ProductField> optionalProductField = productFieldRepository.findById(id);

        if (optionalProductField.isPresent()) {
            ProductField existingPf = optionalProductField.get();
            existingPf.setName(productField.getName());
            existingPf.setStatus(productField.getStatus() != null ? productField.getStatus() : "Active");
            existingPf.setType(productField.getType());



                List<ProductFieldValues> existingPfValues = existingPf.getProductFieldValuesList();
                List<ProductFieldValues> newPfValues = productField.getProductFieldValuesList();
                List<ProductFieldValues> newValuesToAdd = new ArrayList<>();

                for (ProductFieldValues newValue : newPfValues) {
                    Optional<ProductFieldValues> existingValue = existingPfValues.stream()
                            .filter(pfValue -> pfValue.getId().equals(newValue.getId())).findFirst();
                    if (existingValue.isPresent()) {
                        ProductFieldValues existingPfValue = existingValue.get();
                        existingPfValue.setName(newValue.getName());
                        existingPfValue.setStatus(newValue.getStatus() !=null ? newValue.getStatus() : "Active");
                    } else {
                        newValue.setProductField(existingPf);
                        newValuesToAdd.add(newValue);
                    }
                }

                existingPfValues.addAll(newValuesToAdd);

            ProductField updatedPf = productFieldRepository.save(existingPf);
            return toDto(updatedPf);
        } else {
            throw new RuntimeException(String.format("Product Field not found for id => %d", id));
        }
    }


    public void deleteProductFieldValuesById(Long id, Long pfvId) {
        Optional<ProductField> optionalProductField = productFieldRepository.findById(id);
        if (optionalProductField.isPresent()) {
            ProductField productField = optionalProductField.get();

            // Find the ProductFieldValues entity with the provided pfvId
            Optional<ProductFieldValues> optionalProductFieldValues = productField.getProductFieldValuesList()
                    .stream()
                    .filter(pfv -> pfv.getId().equals(pfvId))
                    .findFirst();

            if (optionalProductFieldValues.isPresent()) {
                ProductFieldValues productFieldValuesToDelete = optionalProductFieldValues.get();
                // Remove the ProductFieldValues entity from the list
                productField.getProductFieldValuesList().remove(productFieldValuesToDelete);

                // Delete the ProductFieldValues from the database using the repository
                productFieldValuesRepository.delete(productFieldValuesToDelete);

                // Save the updated ProductField entity to reflect the changes in the database
                productFieldRepository.save(productField);
            } else{
                throw new RuntimeException("Product Field Value not found");
            }
        } else {
            throw new RuntimeException(String.format("Product Field not found for id => %d", id));

        }
    }


    public ProductFieldDto toDto(ProductField productField) {
        List<ProductFieldValuesDto> productFieldValuesDtoList = new ArrayList<>();
        for (ProductFieldValues productFieldValues : productField.getProductFieldValuesList()) {
            ProductFieldValuesDto dto = ProductFieldValuesDto.builder()
                    .id(productFieldValues.getId())
                    .name(productFieldValues.getName())
                    .status(productFieldValues.getStatus())
                    .build();
            productFieldValuesDtoList.add(dto);
        }

        return ProductFieldDto.builder()
                .id(productField.getId())
                .name(productField.getName())
                .status(productField.getStatus())
                .createdAt(productField.getCreatedAt())
                .type(productField.getType())
                .productFieldValuesList(productFieldValuesDtoList)
                .build();
    }

    public ProductField toEntity(ProductFieldDto productFieldDto) {
        ProductField productField = ProductField.builder()
                .id(productFieldDto.getId())
                .name(productFieldDto.getName())
                .status(productFieldDto.getStatus())
                .createdAt(productFieldDto.getCreatedAt())
                .type(productFieldDto.getType())
                .build();

        List<ProductFieldValues> productFieldValuesList = new ArrayList<>();
        for (ProductFieldValuesDto dto : productFieldDto.getProductFieldValuesList()) {
            ProductFieldValues productFieldValues = ProductFieldValues.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .status(dto.getStatus())
                    .build();

            productFieldValuesList.add(productFieldValues);
        }

        productField.setProductFieldValuesList(productFieldValuesList);
        return productField;
    }

}
