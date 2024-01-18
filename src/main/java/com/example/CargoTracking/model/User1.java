package com.example.CargoTracking.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class User1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private boolean status;
    @Column(unique = true)
    private String email;
//    private Set<Location> domesticOriginLocation = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user1_roles",
            joinColumns = @JoinColumn(name = "user1_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "location_id" , referencedColumnName = "id")
//    private Location location;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user1_location",
        joinColumns = @JoinColumn(name = "user1_id") ,
        inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Set<Location> locations = new HashSet<>();
}