package org.jesperancinha.logistics.mcs;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author airhacks.com
 */
@Path("ping")
public class PingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;

    @GET
    public String ping() {
        return this.message + " Jakarta EE with MicroProfile 2+!";
    }

}
