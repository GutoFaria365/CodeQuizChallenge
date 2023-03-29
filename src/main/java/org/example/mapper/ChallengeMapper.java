package org.example.mapper;


import org.example.dto.ChallengeDto;
import org.example.model.Challenge;
import org.mapstruct.Mapper;
@Mapper(componentModel = "cdi")
public interface ChallengeMapper {

    ChallengeDto fromChallengeEntityToChallengeDto (Challenge challenge);
    Challenge fromChallengeDtoToChallengeEntity(ChallengeDto challengeDto);


}
