package org.example.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.dto.ChallengeDto;
import org.example.model.Challenge;
import org.example.service.ChallengeService;
import org.example.controller.GiteaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

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
    public List<ChallengeDto> getAll() {
        return challengeService.findAll();
    }

    @GET
    @Path("name/{name}")
    public ChallengeDto getByName(@PathParam("name") String name) { return challengeService.getByName(name); }

    @GET
    @Path("language/{language}")
    public List<ChallengeDto> getByLanguage(@PathParam("language") String language) { return challengeService.getByLanguage(language); }

    @GET
    @Path("difficulty/{difficulty}")
    public List<ChallengeDto> getByDifficulty(@PathParam("difficulty") String difficulty) { return challengeService.getByDifficulty(difficulty); }

    @GET
    @Path("creator/{creator}")
    public List<ChallengeDto> getByCreator(@PathParam("creator") String creator) { return challengeService.getByCreator(creator); }

    @GET
    @Path("search/{attribute}")
    public List<ChallengeDto> getByBeginsGSI1(@PathParam("attribute") String attribute) { return challengeService.getBeginsWith(attribute); }

    @POST
    public ChallengeDto add(ChallengeDto challenge) {

        challengeService.add(challenge);
        return challenge;
    }

    @DELETE
    @Path("/{name}")
    public void deleteChallenge(@PathParam("name") String name) {
        challengeService.deleteChallenge(name);
    }

}
