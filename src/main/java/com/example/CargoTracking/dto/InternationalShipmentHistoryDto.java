package com.example.CargoTracking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class InternationalShipmentHistoryDto {
    private Long id;
    private String status;
    private LocalDateTime processTime;
    private String locationCode;
    private String user;
    private String remarks;
    private String type;
}
