package com.example.CargoTracking.payload;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<L> {
    private String message;
    private int statusCode;
    private List<?> result;
}
