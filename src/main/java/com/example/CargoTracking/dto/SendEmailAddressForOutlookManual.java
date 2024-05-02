package com.example.CargoTracking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendEmailAddressForOutlookManual {
  private String to;
  private String cc;
  private String subject;
}
