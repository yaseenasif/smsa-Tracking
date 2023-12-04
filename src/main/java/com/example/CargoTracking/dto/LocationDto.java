package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.DestinationEmails;
import com.example.CargoTracking.model.EscalationEmails;
import com.example.CargoTracking.model.OriginEmails;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationDto {

    private Long id;
    @NotBlank(message = "Location name is required")
    private String locationName;
    private String type;
    private boolean status;
    private List<OriginEmails> originEmailsList;
    private List<DestinationEmails> destinationEmailsList;
    private List<EscalationEmails> escalationEmailsList;

}
