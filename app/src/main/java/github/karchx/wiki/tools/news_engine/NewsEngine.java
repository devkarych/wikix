package github.karchx.wiki.tools.news_engine;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class NewsEngine {

    int tokenIndex = 0;

    TokenManager tokenManager = new TokenManager();
    NewsApiClient newsApiClient = new NewsApiClient(tokenManager.getToken(tokenIndex));

    public ArrayList<NewsArticleItem> getNews(String userLang) {
        ArrayList<NewsArticleItem> newsArticles = new ArrayList<>();

        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language(userLang)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        List<Article> responseArray = response.getArticles();
                        for (Article article : responseArray) {
                            // In some responses, there isn't url to image
                            if (article.getUrlToImage() == null) {
                                newsArticles.add(new NewsArticleItem(
                                        article.getTitle(),
                                        article.getDescription(),
                                        article.getPublishedAt(),
                                        "",
                                        article.getUrl()));
                            } else {
                                newsArticles.add(new NewsArticleItem(
                                        article.getTitle(),
                                        article.getDescription(),
                                        article.getPublishedAt(),
                                        article.getUrlToImage(),
                                        article.getUrl()));
                            }
                        }

                        System.out.println(newsArticles);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );

        return newsArticles;
    }
}