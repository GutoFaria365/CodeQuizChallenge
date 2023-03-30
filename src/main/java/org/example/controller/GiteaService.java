package org.example.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1")
@RegisterRestClient
public interface GiteaService {

    @GET
    @Path("user")
    String getById(@HeaderParam("Authorization") String authorization);

    @POST
    @Path("user/repos")
    @Consumes(MediaType.APPLICATION_JSON)
    String createRepo(@HeaderParam("Authorization") String authorization, String json);

    @POST
    @Path("/repos/{owner}/{repo}/forks")
    String createFork(@HeaderParam("Authorization") String authorization, String json,@PathParam("owner") String owner,@PathParam("repo") String repo);

    @DELETE
    @Path("/repos/{owner}/{repo}")
    String deleteRepo(@HeaderParam("Authorization") String authorization, @PathParam("owner") String owner, @PathParam("repo") String repo);

}
