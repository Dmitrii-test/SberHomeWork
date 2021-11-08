package ru.dmitrii.assured.pojos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

        @JsonProperty
        private String name;

        @JsonProperty
        private String job;
    }

