package com.example.CargoTracking.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class ShipmentModeDto {

    private Long id;
    @NotBlank(message = "Shipment Mode is required")
    private String name;

}
