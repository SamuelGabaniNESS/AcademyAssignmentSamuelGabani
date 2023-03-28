package sk.ness.academy.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import sk.ness.academy.controller.BlogController;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
      return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public List<Article> searchAll(String searchedText) {
    return this.articleDAO.searchAll(searchedText);
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    JSONArray array = null;
    try {
      array = new JSONArray(jsonArticles);
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
    for(int i=0;i<array.length();i++){
      try {
        JSONObject object = array.getJSONObject(i);
        Article article = new Article();
        article.setAuthor((String) object.get("author"));
        article.setTitle((String) object.get("title"));
        article.setText((String) object.get("text"));
        createArticle(article);
//        System.out.println(object);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

  }

  @Override
  public void deleteArticle(final Article article) {
    this.articleDAO.deleteById(article);
  }


}
