package org.example.dto;

import org.example.model.Challenge;
import org.example.service.AbstractChallengeService;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Objects;

public class ChallengeDto {
    private String name;
    private String description;
    private String difficulty;
    private String language;
    private String createdAt;
    private String createdBy;
    private String repository;
    private String cloneRepository;
    private String otherInfo;

    private String pk;
    private String sk;
    private String gsi1pk;
    private String gsi1sk;
    private String gsi2pk;
    private String gsi2sk;
    private String gsi3pk;
    private String gsi3sk;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;

    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getCloneRepository() {
        return cloneRepository;
    }

    public void setCloneRepository(String cloneRepository) {
        this.cloneRepository = cloneRepository;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getGsi1pk() {
        return gsi1pk;
    }

    public void setGsi1pk(String gsi1pk) {
        this.gsi1pk = gsi1pk;
    }

    public String getGsi1sk() {
        return gsi1sk;
    }

    public void setGsi1sk(String gsi1sk) {
        this.gsi1sk = gsi1sk;
    }

    public String getGsi2pk() {
        return gsi2pk;
    }

    public void setGsi2pk(String gsi2pk) {
        this.gsi2pk = gsi2pk;
    }

    public String getGsi2sk() {
        return gsi2sk;
    }

    public void setGsi2sk(String gsi2sk) {
        this.gsi2sk = gsi2sk;
    }

    public String getGsi3pk() {
        return gsi3pk;
    }

    public void setGsi3pk(String gsi3pk) {
        this.gsi3pk = gsi3pk;
    }

    public String getGsi3sk() {
        return gsi3sk;
    }

    public void setGsi3sk(String gsi3sk) {
        this.gsi3sk = gsi3sk;
    }
}
