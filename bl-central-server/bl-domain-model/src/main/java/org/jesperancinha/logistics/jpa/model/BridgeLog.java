package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bridge_logs")
public record BridgeLog(
) {
}
