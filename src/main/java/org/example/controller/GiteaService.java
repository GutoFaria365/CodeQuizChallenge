package org.example.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/user")
@RegisterRestClient
public interface GiteaService {

    @GET
    String getById(@HeaderParam("Authorization") String authorization);

    @POST
    @Path("repos")
    @Consumes(MediaType.APPLICATION_JSON)
    String createRepo(@HeaderParam("Authorization") String authorization, String json);
}
