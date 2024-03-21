package com.example.CargoTracking.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResetPassword {
  private Long id;
  private String oldPassword;
  private String newPassword;
}
