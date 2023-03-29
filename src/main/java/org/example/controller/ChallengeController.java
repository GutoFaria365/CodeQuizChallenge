package org.example.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.model.Challenge;
import org.example.service.ChallengeService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@ApplicationScoped
@Path("/challenges")
public class ChallengeController {

    @Inject
    ChallengeService challengeService;
    @RestClient
    GiteaService giteaService;

    @GET
    @Path("/user")
    public String getUser(){
        return giteaService.getById("token 0673f011ed89245efaa3eb76071654183106bc35");
    }
    @POST
    @Path("/user/repos")
    public String createRepo(String json){
        return giteaService.createRepo("token 0673f011ed89245efaa3eb76071654183106bc35", json);
    }
    @GET
    public List<Challenge> getAll() {
        return challengeService.findAll();
    }

    @GET
    @Path("name/{name}")
    public Challenge getByName(@PathParam("name") String name) { return challengeService.getByName(name); }

    @GET
    @Path("language/{language}")
    public List<Challenge> getByLanguage(@PathParam("language") String language) { return challengeService.getByLanguage(language); }

    @GET
    @Path("difficulty/{difficulty}")
    public List<Challenge> getByDifficulty(@PathParam("difficulty") String difficulty) { return challengeService.getByDifficulty(difficulty); }

    @GET
    @Path("creator/{creator}")
    public List<Challenge> getByCreator(@PathParam("creator") String creator) { return challengeService.getByCreator(creator); }

    @POST
    public List<Challenge> add(Challenge challenge) {
        challengeService.add(challenge);
        String json ="{"
                + "\"auto_init\": true,"
                + "\"default_branch\": \"string\","
                + "\"description\": \"string\","
                + "\"gitignores\": \"\","
                + "\"issue_labels\": \"Default\","
                + "\"license\": \"\","
                + "\"name\": \"string3\","
                + "\"private\": true,"
                + "\"readme\": \"Default\","
                + "\"template\": true,"
                + "\"trust_model\": \"default\""
                + "}";
        giteaService.createRepo("token 0673f011ed89245efaa3eb76071654183106bc35", json);
        return getAll();
    }

}
