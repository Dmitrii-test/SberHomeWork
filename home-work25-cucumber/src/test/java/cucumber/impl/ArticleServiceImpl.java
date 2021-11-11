package cucumber.impl;


import cucumber.context.RunContext;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;
import cucumber.config.TestConfig;
import cucumber.model.ArticlesPojo;
import cucumber.service.ArticleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;

@Log4j2
public class ArticleServiceImpl implements ArticleService {
    TestConfig testConfig = new TestConfig();

    @Override
    public List<ArticlesPojo> getArticles(String url) {
        String URL = testConfig.getURL() + url;
        List<ArticlesPojo> articles = new ArrayList<>();

        ValidatableResponse rs = given().log().everything()
                .get(URL)
                .then().log().ifError();

        RunContext.RUN_CONTEXT.put("response", rs);

        try {
            articles = rs.extract().jsonPath().getList("articles.", ArticlesPojo.class);
        }
        catch (Exception e) {
            log.error("Articles request exception: " + Arrays.toString(e.getStackTrace()));
        }
        return articles;

    }
}

