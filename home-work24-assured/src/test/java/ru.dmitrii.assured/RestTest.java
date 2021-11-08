package ru.dmitrii.assured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import ru.dmitrii.assured.pojos.CreateUserRequest;
import ru.dmitrii.assured.pojos.CreateUserResponse;
import ru.dmitrii.assured.pojos.UserPojoFull;
import ru.dmitrii.assured.steps.UserSteps;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class RestTest {
    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api/")
                    .setBasePath("/users")
                    .setContentType(ContentType.JSON)
                    .build();

    @Test
    public void getUsers() {
//        List<UserPojoFull> users =
//                given()
//                        .spec(REQUEST_SPECIFICATION)
//                        .when().get()
//                        .then().statusCode(200)
//                        .extract().jsonPath().getList("data", UserPojoFull.class);

        List<UserPojoFull> users = UserSteps.getUsers();

        assertThat(users).extracting(UserPojoFull::getEmail).contains("george.bluth@reqres.in");
    }

    @Test
    public void createUser() {
        CreateUserRequest rq = new CreateUserRequest();
        rq.setName("simple");
        rq.setJob("automation");

        CreateUserResponse rs = given()
                .baseUri("https://reqres.in/api/")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(rq)
                .when().post()
                .then().extract().as(CreateUserResponse.class);

        assertThat(rs)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(rq.getName());
    }
}

