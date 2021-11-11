package cucumber.service;

import cucumber.model.ArticlesPojo;

import java.util.List;

public interface ArticleService {
    List<ArticlesPojo> getArticles(String URL);
}
