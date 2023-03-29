package org.example.controller;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

@Path("/api/v1/user")
@RegisterRestClient
public interface GiteaService {

    @GET
    String getById(@HeaderParam("Authorization") String authorization);
}
