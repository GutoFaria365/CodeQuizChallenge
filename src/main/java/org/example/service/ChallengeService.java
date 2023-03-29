package org.example.service;

import org.example.dto.ChallengeDto;
import org.example.mapper.ChallengeMapper;
import org.example.model.Challenge;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ChallengeService extends AbstractChallengeService {

    @Inject
    DynamoDbClient dynamoDb;

    ChallengeMapper challengeMapper;

    public List<ChallengeDto> findAll() {
        List<Challenge> challenges = dynamoDb.scanPaginator(scanRequest()).items().stream()
                .map(Challenge::from)
                .collect(Collectors.toList());
        return challenges.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public ChallengeDto add(ChallengeDto challengeDto) {
        Challenge challenge = challengeMapper.fromChallengeDtoToChallengeEntity(challengeDto);
        dynamoDb.putItem(putRequest(challenge));
        return challengeMapper.fromChallengeEntityToChallengeDto(challenge);
    }

    public ChallengeDto getByName(String name) {
        Challenge challengeItem = new Challenge();
        QueryResponse response = dynamoDb.query(getSingleRequest(name));
        for (Map<String, AttributeValue> item : response.items()) {
            challengeItem.setName(item.get("name").s());
            challengeItem.setDescription(item.get("description").s());
            challengeItem.setLanguage(item.get("language").s());
            challengeItem.setDifficulty(item.get("difficulty").s());
            challengeItem.setCreatedBy(item.get("createdBy").s());
            challengeItem.setCreatedAt(item.get("createdAt").s());
        }
        return challengeMapper.fromChallengeEntityToChallengeDto(challengeItem);
    }

    public List<ChallengeDto> getByLanguage(String language){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(language, "LANGUAGE", "GSI1PK", "GSI1SK"));

        for (Map<String, AttributeValue> item : response.items()) {
            Challenge i = new Challenge();
            i.setName(item.get("name").s());
            i.setDescription(item.get("description").s());
            i.setLanguage(item.get("language").s());
            i.setDifficulty(item.get("difficulty").s());
            i.setCreatedBy(item.get("createdBy").s());
            i.setCreatedAt(item.get("createdAt").s());
            items.add(i);
        }
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public List<ChallengeDto> getByDifficulty (String difficulty){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(difficulty, "DIFFICULTY", "GSI2PK", "GSI2SK"));

        for (Map<String, AttributeValue> item : response.items()) {
            Challenge i = new Challenge();
            i.setName(item.get("name").s());
            i.setDescription(item.get("description").s());
            i.setLanguage(item.get("language").s());
            i.setDifficulty(item.get("difficulty").s());
            i.setCreatedBy(item.get("createdBy").s());
            i.setCreatedAt(item.get("createdAt").s());
            items.add(i);
        }
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public List<ChallengeDto> getByCreator (String creator){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(creator, "CREATOR", "GSI3PK", "GSI3SK"));

        for (Map<String, AttributeValue> item : response.items()) {
            Challenge i = new Challenge();
            i.setName(item.get("name").s());
            i.setDescription(item.get("description").s());
            i.setLanguage(item.get("language").s());
            i.setDifficulty(item.get("difficulty").s());
            i.setCreatedBy(item.get("createdBy").s());
            i.setCreatedAt(item.get("createdAt").s());
            items.add(i);
        }
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }
}
