package cucumber.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticlesPojo {

    @JsonProperty("date")
    private String date;

    @JsonProperty("layout")
    private String layout;

    @JsonProperty("categories")
    private List<String> categories;

    @JsonProperty("title")
    private String title;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("url")
    private String url;

    @JsonProperty("content")
    private String content;

    @JsonProperty("tags")
    private List<Object> tags;

    public String getDate(){
        return date;
    }

    public String getLayout(){
        return layout;
    }

    public List<String> getCategories(){
        return categories;
    }

    public String getTitle(){
        return title;
    }

    public String getLang(){
        return lang;
    }

    public String getUrl(){
        return url;
    }

    public String getContent(){
        return content;
    }

    public List<Object> getTags(){
        return tags;
    }
}
