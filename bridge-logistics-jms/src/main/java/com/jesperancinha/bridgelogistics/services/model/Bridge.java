package com.jesperancinha.bridgelogistics.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "Bridge")
public class Bridge {

    @Id
    @Column(name = "BridgeID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String address;

    private String city;

    private String postCode;

    private String country;

}
