package org.example.service;

import org.example.model.Challenge;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractChallengeService {

    public final static String PK = "PK";
    public final static String SK = "SK";
    public final static String GSI1PK = "GSI1PK";
    public final static String GSI1SK = "GSI1SK";
    public final static String GSI2PK = "GSI2PK";
    public final static String GSI2SK = "GSI2SK";
    public final static String GSI3PK = "GSI3PK";
    public final static String GSI3SK = "GSI3SK";
    public final static String C_NAME = "name";
    public final static String C_DESCRIPTION = "description";
    public final static String C_CREATED_AT = "createdAt";
    public final static String C_CREATED_BY = "createdBy";
    public final static String C_DIFFICULTY = "difficulty";
    public final static String C_LANGUAGE = "language";
    public final static String C_REPOSITORY = "repository";
    public final static String C_CLONE_REP = "cloneRepository";
    public final static String C_OTHER_INFO = "otherInfo";
    public final static boolean C_PUBLISHED = false;

    public String getTableName() {
        return "Challenges";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(C_NAME, C_DESCRIPTION, C_LANGUAGE, C_DIFFICULTY, C_CREATED_BY, C_CREATED_AT).build();
    }

    protected PutItemRequest putRequest(Challenge challenge) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(PK, AttributeValue.builder().s("CHALLENGE#" + challenge.getName().toUpperCase()).build());
        item.put(SK, AttributeValue.builder().s("CHALLENGE#" + challenge.getName().toUpperCase()).build());
        item.put(C_NAME, AttributeValue.builder().s(challenge.getName()).build());
        item.put(C_DESCRIPTION, AttributeValue.builder().s(challenge.getDescription()).build());
        item.put(C_LANGUAGE, AttributeValue.builder().s(challenge.getLanguage()).build());
        item.put(C_DIFFICULTY, AttributeValue.builder().s(challenge.getDifficulty()).build());
        item.put(C_CREATED_BY, AttributeValue.builder().s(challenge.getCreatedBy()).build());
        item.put(C_CREATED_AT, AttributeValue.builder().s(challenge.getCreatedAt()).build());
        item.put(C_REPOSITORY, AttributeValue.builder().s(challenge.getRepository()).build());
        item.put(C_CLONE_REP, AttributeValue.builder().s(challenge.getCloneRepository()).build());
        item.put(C_OTHER_INFO, AttributeValue.builder().s(challenge.getOtherInfo()).build());
        item.put(GSI1PK, AttributeValue.builder().s("CHALLENGE#LANGUAGE").build());
        item.put(GSI1SK, AttributeValue.builder().s("CHALLENGE#LANGUAGE#" + challenge.getLanguage().toUpperCase()).build());
        item.put(GSI2PK, AttributeValue.builder().s("CHALLENGE#DIFFICULTY").build());
        item.put(GSI2SK, AttributeValue.builder().s("CHALLENGE#DIFFICULTY#" + challenge.getDifficulty().toUpperCase()).build());
        item.put(GSI3PK, AttributeValue.builder().s("CHALLENGE#CREATOR").build());
        item.put(GSI3SK, AttributeValue.builder().s("CHALLENGE#CREATOR#" + challenge.getCreatedBy().toUpperCase()).build());

        challenge.setPk("CHALLENGE#" + challenge.getName().toUpperCase());
        challenge.setSk("CHALLENGE#" + challenge.getName().toUpperCase());
        challenge.setGsi1pk("CHALLENGE#LANGUAGE");
        challenge.setGsi1sk("CHALLENGE#LANGUAGE#" + challenge.getLanguage().toUpperCase());
        challenge.setGsi2pk("CHALLENGE#DIFFICULTY");
        challenge.setGsi2sk("CHALLENGE#DIFFICULTY#" + challenge.getDifficulty().toUpperCase());
        challenge.setGsi3pk("CHALLENGE#CREATOR");
        challenge.setGsi3sk("CHALLENGE#CREATOR#" + challenge.getCreatedBy().toUpperCase());


        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }
    public QueryRequest getSingleRequest(String name) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":pk", AttributeValue.builder().s("CHALLENGE#".concat(name.toUpperCase())).build());
        expressionAttributeValues.put(":sk", AttributeValue.builder().s("CHALLENGE#".concat(name.toUpperCase())).build());

        return QueryRequest.builder()
                .tableName(getTableName())
                .keyConditionExpression("PK= :pk and SK= :sk")
                .expressionAttributeValues(expressionAttributeValues)
                .build();
    }
    public QueryRequest getQueryRequest(String attribute, String type, String gsiPk, String gsiSk) {
        Map<String, AttributeValue> tempValues = new HashMap<>();
        tempValues.put(":" + gsiPk.toLowerCase(), AttributeValue.builder().s("CHALLENGE#".concat(type)).build());
        tempValues.put(":" + gsiSk.toLowerCase(), AttributeValue.builder().s("CHALLENGE#".concat(type).concat("#" + attribute.toUpperCase())).build());

        return QueryRequest.builder()
                .tableName(getTableName())
                .indexName(gsiPk.toUpperCase() + "_" + gsiSk.toUpperCase())
                .keyConditionExpression(gsiPk.toUpperCase() + "= :" + gsiPk.toLowerCase() + " and " + gsiSk.toUpperCase() + "= :" + gsiSk.toLowerCase())
                .expressionAttributeValues(tempValues)
                .build();
    }
    public DeleteItemRequest deleteItemRequest(String name){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("PK", AttributeValue.builder()
                .s("CHALLENGE#".concat(name.toUpperCase()))
                .build());
        key.put("SK", AttributeValue.builder()
                .s("CHALLENGE#".concat(name.toUpperCase()))
                .build());

        return DeleteItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();

    }

    public QueryRequest getBeginsWithRequest(String attribute, String type, String gsiPk, String gsiSk) {
        String[] parts = attribute.split("-");
        String first = parts[0];
        String second = parts[1];

        Map<String, AttributeValue> tempValues = new HashMap<>();
        tempValues.put(":" + gsiPk.toLowerCase(), AttributeValue.builder().s("CHALLENGE#".concat(type)).build());
        tempValues.put(":" + gsiSk.toLowerCase(), AttributeValue.builder().s(first.toUpperCase().concat("#" + second.toUpperCase())).build());

        return QueryRequest.builder()
                .tableName(getTableName())
                .indexName(gsiPk.toUpperCase() + "_" + gsiSk.toUpperCase())
                .keyConditionExpression(gsiPk.toUpperCase() + "= :" + gsiPk.toLowerCase() + " and begins_with(" + gsiSk.toUpperCase() + ", :" + gsiSk.toLowerCase() + ")")
                .expressionAttributeValues(tempValues)
                .build();
    }

    protected UpdateItemRequest updateRequest(Challenge challenge) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(PK, AttributeValue.builder().s("CHALLENGE#" + challenge.getName().toUpperCase()).build());
        key.put(SK, AttributeValue.builder().s("CHALLENGE#" + challenge.getName().toUpperCase()).build());

        Map<String, AttributeValueUpdate> updates = new HashMap<>();
        updates.put(C_NAME, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getName()).build()).build());
        updates.put(C_DESCRIPTION, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getDescription()).build()).build());
        updates.put(C_LANGUAGE, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getLanguage()).build()).build());
        updates.put(C_DIFFICULTY, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getDifficulty()).build()).build());
        updates.put(C_CREATED_BY, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getCreatedBy()).build()).build());
        updates.put(C_CREATED_AT, AttributeValueUpdate.builder().value(AttributeValue.builder().s(challenge.getCreatedAt()).build()).build());
        updates.put("GSI1SK", AttributeValueUpdate.builder().value(AttributeValue.builder().s("CHALLENGE#LANGUAGE#" + challenge.getLanguage().toUpperCase()).build()).build());
        updates.put("GSI2SK", AttributeValueUpdate.builder().value(AttributeValue.builder().s("CHALLENGE#DIFFICULTY#" + challenge.getDifficulty().toUpperCase()).build()).build());
        updates.put("GSI3SK", AttributeValueUpdate.builder().value(AttributeValue.builder().s("CHALLENGE#CREATOR#" + challenge.getCreatedBy().toUpperCase()).build()).build());

        return UpdateItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributeUpdates(updates)
                .build();
    }
}
