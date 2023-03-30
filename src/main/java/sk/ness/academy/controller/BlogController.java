package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exceptions.*;
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
    List<Article> result = this.articleService.findAll();
    if(result.isEmpty()){
      throw new ArticleNotFoundException();
    }else {
      return result;
    }
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
    Article result = this.articleService.findByID(articleId);
    if(result == null){
      throw new ArticleNotFoundException();
    }else {
      return result;
    }
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
    List<Article> result = this.articleService.searchAll(searchText);
    if(result.isEmpty()){
      throw new ArticleNotFoundException(searchText);
    }else {
      return result;
    }
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {
    if(article.getAuthor().isEmpty() || article.getTitle().isEmpty() || article.getText().isEmpty()){
      throw new ArticleIllegalArgumentException(article.getAuthor(),article.getTitle(),article.getText());
    }else {
      this.articleService.createArticle(article);
    }
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
    List<Author> result = this.authorService.findAll();
    if(result.isEmpty()){
      throw new AuthorNotFoundException();
    }else {
      return result;
    }
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
    List<AuthorStats> result = this.authorService.getAuthorStats();
    if(result.isEmpty()){
      throw new AuthorNotFoundException();
    }else{
      return result;
    }
  }

  // Delete article
  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
    if(this.articleService.findByID(articleId) == null){
      throw new ArticleNotFoundException(articleId);
    }else {
      this.articleService.deleteArticle(this.articleService.findByID(articleId));
    }
  }



  // Comments
  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public List<Comment> getAllComments(@PathVariable final Integer articleId) {
    List<Comment> result = this.commentService.findAll(articleId);
    if(result.isEmpty()){
      throw new CommentsNotFoundException(articleId);
    }else {
      return result;
    }
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@PathVariable final Integer articleId,@RequestBody final Comment comment) {
    if(this.articleService.findByID(articleId)!=null) {
      if(comment.getAuthor().isEmpty() || comment.getText().isEmpty()){
        throw new CommentIllegalArgumentException(comment.getAuthor(),comment.getText());
      }else{
        comment.setId_article(articleId);
        this.commentService.createComment(comment);
      }
    }else{
      throw new CommentsNotFoundException(articleId);
    }
  }

  @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer articleId,@PathVariable final Integer commentId) {
    if(this.commentService.findByID(commentId)==null){
      throw new CommentsNotFoundException(articleId,commentId);
    }else {
      this.commentService.deleteComment(this.commentService.findByID(commentId));
    }
  }

}
