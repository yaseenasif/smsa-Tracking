package com.example.CargoTracking.dto;


import com.example.CargoTracking.model.ProductField;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductFieldValuesDto {

    private Long id;

    private String name;
    private String status;

}
