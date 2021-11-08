package ru.dmitrii.assured.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserResponse {
    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("job")
    private String job;

    public String getCreatedAt(){
        return createdAt;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getJob(){
        return job;
    }
}