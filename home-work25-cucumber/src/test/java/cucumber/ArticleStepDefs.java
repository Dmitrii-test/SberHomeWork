package cucumber;

import cucumber.impl.ArticleServiceImpl;
import cucumber.model.ArticlesPojo;
import cucumber.service.ArticleService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import java.util.List;

import static cucumber.context.RunContext.RUN_CONTEXT;


public class ArticleStepDefs {
    ArticleService articleService = new ArticleServiceImpl();

    @Given("Get Articles {string} Request")
    public void getArticlesRequest(String URL) {
        List<ArticlesPojo> articlesList = articleService.getArticles(URL);
    }

    @Then("Response code is: {string}")
    public void responseCodeIs(String status) {
        ValidatableResponse response = RUN_CONTEXT.get("response", ValidatableResponse.class);
        int actualStatus = response.extract().statusCode();
        int expectedStatus = Integer.parseInt(status);
        Assert.assertEquals(actualStatus,expectedStatus);
    }
}
