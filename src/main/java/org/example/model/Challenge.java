package org.example.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.example.service.AbstractChallengeService;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Objects;

@RegisterForReflection
public class Challenge {

    private String name;

    private String description;

    private String difficulty;

    private String language;

    private String createdAt;

    private String createdBy;

    public static Challenge from(Map<String, AttributeValue> item) {
        Challenge challenge = new Challenge();
        if (item != null && !item.isEmpty()) {
            challenge.setName(item.get(AbstractChallengeService.C_NAME).s());
            challenge.setDescription(item.get(AbstractChallengeService.C_DESCRIPTION).s());
            challenge.setLanguage(item.get(AbstractChallengeService.C_LANGUAGE).s());
            challenge.setDifficulty(item.get(AbstractChallengeService.C_DIFFICULTY).s());
            challenge.setCreatedBy(item.get(AbstractChallengeService.C_CREATED_BY).s());
            challenge.setCreatedAt(item.get(AbstractChallengeService.C_CREATED_AT).s());

        }
        return challenge;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getLanguage() {
        return language;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Challenge)) {
            return false;
        }

        Challenge other = (Challenge) obj;

        return Objects.equals(other.name, this.name);
    }
    }

