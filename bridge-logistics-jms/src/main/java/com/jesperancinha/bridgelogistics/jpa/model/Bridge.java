package com.jesperancinha.bridgelogistics.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name="Bridge.findAll",
                query="SELECT b FROM Bridge b")
})
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
