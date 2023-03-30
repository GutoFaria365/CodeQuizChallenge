package org.example.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.model.Challenge;
import org.example.service.ChallengeService;
import org.example.service.GiteaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/challenges")
public class ChallengeController {

    @Inject
    ChallengeService challengeService;
//    @RestClient
//    GiteaService giteaService;

//    @GET
//    @Path("/user")
//    public String getUser(){
//        return giteaService.getById("token 0673f011ed89245efaa3eb76071654183106bc35");
//    }

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
        return getAll();
    }

    @PATCH
    @Path("update/{challengeName}")
    public Challenge patch(@PathParam("challengeName") String challengeName, Challenge challenge) {
        challenge.setName(challengeName);
        challengeService.patch(challenge);
        return challenge;
    }

}
