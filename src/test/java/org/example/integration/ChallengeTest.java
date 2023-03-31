package org.example.integration;


import io.quarkus.test.junit.QuarkusTest;
import org.example.controller.ChallengeController;
import org.example.dto.ChallengeDto;
import org.example.mapper.ChallengeMapper;
import org.example.model.Challenge;
import org.example.service.ChallengeService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;


@QuarkusTest

public class ChallengeTest {

    @Inject
    ChallengeService challengeService;

    @Inject
    Challenge challenge;

    @Inject
    ChallengeMapper challengeMapper;

    @Inject
    ChallengeController challengeController;


    @Test
    public void testInsertChallenge() {
        challenge.setName("integrationTest");
        challenge.setDescription("test");
        challenge.setDifficulty("0");
        challenge.setLanguage("java");
        challenge.setCreatedAt("now");
        challenge.setCreatedBy("test");

        ChallengeDto challengeDto = challengeMapper.fromChallengeEntityToChallengeDto(challenge);


        challengeController.add(challengeDto);

        challengeController.deleteChallenge("integrationTest");
    }
}