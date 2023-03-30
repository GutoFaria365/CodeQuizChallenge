package org.example.service;

import org.example.controller.GiteaService;
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

    @Inject
    GiteaService giteaService;

    public List<Challenge> findAll() {
        return dynamoDb.scanPaginator(scanRequest()).items().stream()
                .map(Challenge::from)
                .collect(Collectors.toList());
    }

    public Challenge add(Challenge challenge) {
        String json ="{"
                + "\"auto_init\": true,"
                + "\"default_branch\": \"string\","
                + "\"description\": \"" + challenge.getDescription() + "\","
                + "\"gitignores\": \"\","
                + "\"issue_labels\": \"Default\","
                + "\"license\": \"\","
                + "\"name\": \"" + challenge.getName() + "\","
                + "\"private\": true,"
                + "\"readme\": \"Default\","
                + "\"template\": true,"
                + "\"trust_model\": \"default\""
                + "}";
        giteaService.createRepo("token 0673f011ed89245efaa3eb76071654183106bc35", json);

        dynamoDb.putItem(putRequest(challenge));
        return challenge;
    }

    public Challenge getByName(String name) {
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
        return challengeItem;
    }

    public List<Challenge> getByLanguage(String language){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(language, "LANGUAGE", "GSI1PK", "GSI1SK"));

        return getChallenges(items, response);
    }

    public List<Challenge> getByDifficulty (String difficulty){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(difficulty, "DIFFICULTY", "GSI2PK", "GSI2SK"));

        return getChallenges(items, response);
    }

    public List<Challenge> getByCreator (String creator){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getQueryRequest(creator, "CREATOR", "GSI3PK", "GSI3SK"));

        return getChallenges(items, response);
    }

    public List<Challenge> getBeginsWith (String language){
        List<Challenge> items = new ArrayList<>();

        QueryResponse response = dynamoDb.query(getBeginsWithRequest(language, "LANGUAGE", "GSI1PK", "GSI1SK"));

        return getChallenges(items, response);
    }
    private static List<Challenge> getChallenges(List<Challenge> items, QueryResponse response) {
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
        return items;
    }
}
