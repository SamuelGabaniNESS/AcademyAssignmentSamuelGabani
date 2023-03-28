package sk.ness.academy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hsqldb.HsqlException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.domain.MyResourceNotFoundException;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles(){
    return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
	  return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
	  return this.articleService.searchAll(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {
	  this.articleService.createArticle(article);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
	  return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
	  return this.authorService.getAuthorStats();
  }

  // Delete article
  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
    this.articleService.deleteArticle(this.articleService.findByID(articleId));
  }



  // Comments
  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public List<Comment> getAllComments(@PathVariable final Integer articleId) {
    return this.commentService.findAll(articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@PathVariable final Integer articleId,@RequestBody final Comment comment) {
    if(this.articleService.findByID(articleId)!=null) {
      comment.setId_article(articleId);
      this.commentService.createComment(comment);
    }
  }

  @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer articleId,@PathVariable final Integer commentId) {
    this.commentService.deleteComment(this.commentService.findByID(commentId));
  }


  // Exception handling



//  @ResponseStatus(value = HttpStatus.ACCEPTED)
//  @RequestMapping(value="/articlese", method=RequestMethod.GET)
//  @ResponseBody
//  public String skuska() {
//    return "not found";
//  }


}
