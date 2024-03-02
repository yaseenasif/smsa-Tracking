package com.example.CargoTracking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailAddressForRoutesDto {
  private Long id;
  private String emails;
  private String type;
}
