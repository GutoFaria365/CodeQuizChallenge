package org.example.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.dto.ChallengeDto;
import org.example.model.Challenge;
import org.example.service.ChallengeService;
import org.example.controller.GiteaService;
import org.example.utils.Messages;

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

    @POST
    @Path("/repos/{owner}/{repo}/forks")
    public String createFork(String json, @PathParam("owner") String owner, @PathParam("repo") String repo) {

        return giteaService.createFork("token 0673f011ed89245efaa3eb76071654183106bc35",json,owner,repo);
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
    public ChallengeDto add(ChallengeDto challengeDto) {

        challengeService.add(challengeDto);
        return challengeDto;
    }

    @DELETE
    @Path("/delete/{name}")
    public String deleteChallenge(@PathParam("name") String name) {
        challengeService.deleteChallenge(name);
        return Messages.CHAL_DELETED;
    }

    @PATCH
    @Path("update/{challengeName}")
    public Challenge patch(@PathParam("challengeName") String challengeName, Challenge challenge) {
        challenge.setName(challengeName);
        challengeService.patch(challenge);
        return challenge;
    }

}
