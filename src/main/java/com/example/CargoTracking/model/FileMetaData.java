package com.example.CargoTracking.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
@Entity
public class FileMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileExtension;
    private String attachmentType;

    @ManyToOne
    @JoinColumn(name = "domestic_shipment_id")
    private DomesticShipment domesticShipment;

    @ManyToOne
    @JoinColumn(name = "international_shipment_id")
    private InternationalShipment internationalShipment;
}
