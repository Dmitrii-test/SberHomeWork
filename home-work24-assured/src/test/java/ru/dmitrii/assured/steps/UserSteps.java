package ru.dmitrii.assured.steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.dmitrii.assured.pojos.UserPojoFull;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserSteps {

    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api/")
                    .setBasePath("/users")
                    .setContentType(ContentType.JSON)
                    .build();

    public static List<UserPojoFull> getUsers() {
        return given().spec(REQUEST_SPECIFICATION)
                .get()
                .jsonPath().getList("data", UserPojoFull.class);
    }
}

