package org.example.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.controller.GiteaService;
import org.example.dto.ChallengeDto;
import org.example.mapper.ChallengeMapper;
import org.example.model.Challenge;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    @Inject
    ChallengeMapper challengeMapper;

    @RestClient
    GiteaService giteaService;


    public ChallengeService(ChallengeMapper challengeMapper) {
        this.challengeMapper = challengeMapper;
    }

    public List<ChallengeDto> findAll() {
        List<Challenge> challenges = dynamoDb.scanPaginator(scanRequest()).items().stream()
                .map(Challenge::from)
                .collect(Collectors.toList());
        return challenges.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public ChallengeDto add(ChallengeDto challengeDto) {
        String json = "{"
                + "\"auto_init\": true,"
                + "\"default_branch\": \"string\","
                + "\"description\": \"" + challengeDto.getDescription() + "\","
                + "\"gitignores\": \"\","
                + "\"issue_labels\": \"Default\","
                + "\"license\": \"\","
                + "\"name\": \"" + challengeDto.getName() + "\","
                + "\"private\": true,"
                + "\"readme\": \"Default\","
                + "\"template\": true,"
                + "\"trust_model\": \"default\""
                + "}";

        String url;
        String cloneUrl;
        String jsonString = giteaService.createRepo("token 0673f011ed89245efaa3eb76071654183106bc35", json);

//        Repo repo;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            repo = objectMapper.readValue(json, Repo.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

//        JsonUtils<Repo> jsonUtils = new JsonUtils<>(Repo.class);
//        Repo jsonObject = jsonUtils.fromJson(jsonString);
//
//        url = (String) jsonObject.getHtml_url();
//        cloneUrl = (String) jsonObject.getClone_url();

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            url = (String) jsonObject.get("html_url");
            cloneUrl = (String) jsonObject.get("clone_url");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Challenge challenge = challengeMapper.fromChallengeDtoToChallengeEntity(challengeDto);
        challenge.setRepository(url);
        challenge.setCloneRepository(cloneUrl);
        challenge.setOtherInfo(jsonString);


        dynamoDb.putItem(putRequest(challenge));
        return challengeMapper.fromChallengeEntityToChallengeDto(challenge);
    }


    public Challenge patch(Challenge challenge){
        dynamoDb.updateItem(updateRequest(challenge));
        return challenge;
    }

    public ChallengeDto getByName(String name) {
        Challenge challengeItem = new Challenge();
        QueryResponse response = dynamoDb.query(getSingleRequest(name));
        for (Map<String, AttributeValue> item : response.items()) {
            challengeItem.setPk(item.get("PK").s());
            challengeItem.setSk(item.get("SK").s());
            challengeItem.setName(item.get("name").s());
            challengeItem.setDescription(item.get("description").s());
            challengeItem.setLanguage(item.get("language").s());
            challengeItem.setDifficulty(item.get("difficulty").s());
            challengeItem.setCreatedBy(item.get("createdBy").s());
            challengeItem.setCreatedAt(item.get("createdAt").s());
            challengeItem.setRepository(item.get("repository").s());
            challengeItem.setCloneRepository(item.get("cloneRepository").s());
            challengeItem.setOtherInfo(item.get("otherInfo").s());
            challengeItem.setGsi1pk(item.get("GSI1PK").s());
            challengeItem.setGsi1sk(item.get("GSI1SK").s());
            challengeItem.setGsi2pk(item.get("GSI2PK").s());
            challengeItem.setGsi2sk(item.get("GSI2SK").s());
            challengeItem.setGsi3pk(item.get("GSI3PK").s());
            challengeItem.setGsi3sk(item.get("GSI3SK").s());
        }
        return challengeMapper.fromChallengeEntityToChallengeDto(challengeItem);
    }

    public List<ChallengeDto> getByLanguage(String language){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(language, "LANGUAGE", "GSI1PK", "GSI1SK"));

        getChallenges(items, response);
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public List<ChallengeDto> getByDifficulty (String difficulty){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(difficulty, "DIFFICULTY", "GSI2PK", "GSI2SK"));

        getChallenges(items, response);
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }

    public List<ChallengeDto> getByCreator (String creator){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(creator, "CREATOR", "GSI3PK", "GSI3SK"));

        getChallenges(items, response);
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }
    public List<ChallengeDto> getBeginsWith (String language){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getBeginsWithRequest(language, "LANGUAGE", "GSI1PK", "GSI1SK"));
        getChallenges(items, response);
        return items.stream()
                .map(challengeMapper::fromChallengeEntityToChallengeDto)
                .collect(Collectors.toList());
    }
    private static void getChallenges(List<Challenge> items, QueryResponse response) {
        for (Map<String, AttributeValue> item : response.items()) {
            Challenge i = new Challenge();
            i.setPk(item.get("PK").s());
            i.setSk(item.get("SK").s());
            i.setName(item.get("name").s());
            i.setDescription(item.get("description").s());
            i.setLanguage(item.get("language").s());
            i.setDifficulty(item.get("difficulty").s());
            i.setCreatedBy(item.get("createdBy").s());
            i.setCreatedAt(item.get("createdAt").s());
            i.setRepository(item.get("repository").s());
            i.setCloneRepository(item.get("cloneRepository").s());
            i.setOtherInfo(item.get("otherInfo").s());
            i.setGsi1pk(item.get("GSI1PK").s());
            i.setGsi1sk(item.get("GSI1SK").s());
            i.setGsi2pk(item.get("GSI2PK").s());
            i.setGsi2sk(item.get("GSI2SK").s());
            i.setGsi3pk(item.get("GSI3PK").s());
            i.setGsi3sk(item.get("GSI3SK").s());
            items.add(i);
        }
    }

    public void deleteChallenge (String name) {
        dynamoDb.deleteItem(deleteItemRequest(name));
        giteaService.deleteRepo("token 0673f011ed89245efaa3eb76071654183106bc35", "root", name);

    }

}
