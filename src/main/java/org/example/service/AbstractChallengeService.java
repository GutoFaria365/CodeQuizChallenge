package org.example.service;

import org.example.model.Challenge;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractChallengeService {

    public static final String PK = "PK";
    public static final String SK = "SK";
    public static final String C_NAME = "name";
    public static final String C_DESCRIPTION = "description";
    public static final String C_CREATED_AT = "createdAt";
    public static final String C_CREATED_BY = "createdBy";
    public static final String C_DIFFICULTY = "difficulty";
    public static final String C_LANGUAGE = "language";
    public static final String C_REPOSITORY = "repository";
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
        item.put("GSI1PK", AttributeValue.builder().s("CHALLENGE#LANGUAGE").build());
        item.put("GSI1SK", AttributeValue.builder().s("CHALLENGE#LANGUAGE#" + challenge.getLanguage().toUpperCase()).build());
        item.put("GSI2PK", AttributeValue.builder().s("CHALLENGE#DIFFICULTY").build());
        item.put("GSI2SK", AttributeValue.builder().s("CHALLENGE#DIFFICULTY#" + challenge.getDifficulty().toUpperCase()).build());
        item.put("GSI3PK", AttributeValue.builder().s("CHALLENGE#CREATOR").build());
        item.put("GSI3SK", AttributeValue.builder().s("CHALLENGE#CREATOR#" + challenge.getCreatedBy().toUpperCase()).build());
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
