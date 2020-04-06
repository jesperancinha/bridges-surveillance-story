package org.jesperancinha.logistics.application;

import org.jesperancinha.logistics.services.BridgeOpeningService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/conflicts")
@Stateless
public class BridgeConflictResource {

    @Inject
    private BridgeOpeningService bridgeOpeningService;

    @GET
    public Response getAllConflicts() {
        return Response.ok(bridgeOpeningService.getAllConflicts(new HashMap<>())).build();
    }
}
