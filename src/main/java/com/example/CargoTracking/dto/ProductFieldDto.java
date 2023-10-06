package com.example.CargoTracking.dto;


import com.example.CargoTracking.model.ProductFieldValues;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductFieldDto {
    private Long id;

    private String name;
    private String status;
    private LocalDate createdAt;
    private Type type;
    private List<ProductFieldValuesDto> productFieldValuesList;

}
