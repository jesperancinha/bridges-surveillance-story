package org.jesperancinha.logistics.jpa.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

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
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bridge")
@NamedQuery(name = "Bridge.findBridgeBySquareBoundary",
        query = "select b from Bridge b"
                + " where b.lat>=:latWest and b.lat<=:latEast and b.lon<=:lonNorth and b.lon>=:lonSouth")
public class Bridge {
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
    @ToString.Exclude
    private List<BridgeOpeningTime> bridgeOpeningTimea = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bridge bridge = (Bridge) o;

        return Objects.equals(id, bridge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getCity(), getPostCode(), getCountryCode(), getUnitLength(), getLength(), getLat(), getLon(), getRadius(), getType(), getBridgeOpeningTimea());
    }
}
