package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bridge")
@NamedQuery(name = "Bridge.findBridgeBySquareBoundary",
    query = "select b from Bridge b" + " where b.lat>=:latWest and b.lat<=:latEast and b.lon<=:lonNorth and b.lon>=:lonSouth")
public class  Bridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postCode;
    private String countryCode;
    private String unitLength;
    private Long length;
    @Column(precision = 10,
        scale = 6)
    private BigDecimal lat;
    @Column(precision = 10,
        scale = 6)
    private BigDecimal lon;
    private Long radius;
    private String type;
    @OneToMany(mappedBy = "bridge")
    private List<BridgeOpeningTime> bridgeOpeningTimea = new ArrayList<>();
}
