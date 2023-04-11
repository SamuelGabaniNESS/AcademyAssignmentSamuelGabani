package sk.ness.academy.springboothibernate.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.controller.BlogController;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.service.ArticleServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ArticleServiceImplTest {
    @Mock
    private ArticleDAO articleDAO;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private List<Article> articles;

    private List<Article> searchedArticles;

    @Test
    void testFindById(){
        Mockito.when(articleDAO.findByID(1)).thenReturn(articles.get(0));
        final Article article = articleService.findByID(1);
        Assertions.assertEquals(1,article.getId());
        Assertions.assertEquals("Samo",article.getAuthor());
        Assertions.assertEquals("Java zivot dava",article.getTitle());
        Assertions.assertEquals("Java, tak uzasny programovaci jazyk",article.getText());
        Assertions.assertEquals(2,article.getComments().size());
        Assertions.assertEquals(1,article.getComments().get(0).getId_article());
        Assertions.assertEquals(1,article.getComments().get(0).getId());
        Assertions.assertEquals("Juraj",article.getComments().get(0).getAuthor());
        Assertions.assertEquals("Ok",article.getComments().get(0).getText());
        Assertions.assertEquals(1,article.getComments().get(1).getId_article());
        Assertions.assertEquals(2,article.getComments().get(1).getId());
        Assertions.assertEquals("Filip",article.getComments().get(1).getAuthor());
        Assertions.assertEquals("Fantastic",article.getComments().get(1).getText());
    }

    @Test
    void testFindAll() {
        Mockito.when(articleDAO.findAll()).thenReturn(articles);

        final List<Article> articleList = articleService.findAll();
        // pocet article
        Assertions.assertEquals(3, articleList.size());
        // article 1
        Assertions.assertEquals(1,articleList.get(0).getId());
        Assertions.assertEquals("Samo",articleList.get(0).getAuthor());
        Assertions.assertEquals("Java zivot dava",articleList.get(0).getTitle());
        Assertions.assertEquals("Java, tak uzasny programovaci jazyk",articleList.get(0).getText());
        // pocet komentarov article 1
        Assertions.assertEquals(2,articleList.get(0).getComments().size());
        // komentar 1 k article 1
        Assertions.assertEquals(1,articleList.get(0).getComments().get(0).getId_article());
        Assertions.assertEquals(1,articleList.get(0).getComments().get(0).getId());
        Assertions.assertEquals("Juraj",articleList.get(0).getComments().get(0).getAuthor());
        Assertions.assertEquals("Ok",articleList.get(0).getComments().get(0).getText());
        // komentar 2 k article 1
        Assertions.assertEquals(1,articleList.get(0).getComments().get(1).getId_article());
        Assertions.assertEquals(2,articleList.get(0).getComments().get(1).getId());
        Assertions.assertEquals("Filip",articleList.get(0).getComments().get(1).getAuthor());
        Assertions.assertEquals("Fantastic",articleList.get(0).getComments().get(1).getText());

        // article 2
        Assertions.assertEquals(2,articleList.get(1).getId());
        Assertions.assertEquals("Ivan",articleList.get(1).getAuthor());
        Assertions.assertEquals("Java++",articleList.get(1).getTitle());
        Assertions.assertEquals("Java under the hood",articleList.get(1).getText());
        // pocet komentarov k article 2
        Assertions.assertEquals(1,articleList.get(1).getComments().size());
        // komentar 1 k article 2
        Assertions.assertEquals(2,articleList.get(1).getComments().get(0).getId_article());
        Assertions.assertEquals(1,articleList.get(1).getComments().get(0).getId());
        Assertions.assertEquals("Oliver",articleList.get(1).getComments().get(0).getAuthor());
        Assertions.assertEquals("Super",articleList.get(1).getComments().get(0).getText());

        //article 3
        Assertions.assertEquals(3,articleList.get(2).getId());
        Assertions.assertEquals("Tomas",articleList.get(2).getAuthor());
        Assertions.assertEquals("AI",articleList.get(2).getTitle());
        Assertions.assertEquals("AI will take our jobs",articleList.get(2).getText());
        // pocet komentarov k article 2
        Assertions.assertEquals(0,articleList.get(2).getComments().size());
    }
    
    @Test
    void testSearchAll(){
        String searchText = "Java";
        Mockito.when(articleDAO.searchAll(searchText)).thenReturn(searchedArticles);
        final List<Article> articleList = articleService.searchAll(searchText);
        Assertions.assertEquals(2,articleList.size());
        Assertions.assertEquals(1,articleList.get(0).getId());
        Assertions.assertEquals("Samo",articleList.get(0).getAuthor());
        Assertions.assertEquals("Java zivot dava",articleList.get(0).getTitle());
        Assertions.assertEquals("Java, tak uzasny programovaci jazyk",articleList.get(0).getText());
    }

    @Test
    void testCreateArticle(){
        Article a = new Article();
        a.setId(4);
        a.setTitle("a");
        a.setText("a");
        a.setAuthor("a");
        articleService.createArticle(a);
        Mockito.verify(articleDAO).persist(a);
    }

    @Test
    void testDeleteArticle(){
        Article a = new Article();
        a.setId(4);
        a.setTitle("a");
        a.setText("a");
        a.setAuthor("a");
        articleService.deleteArticle(a);
        Mockito.verify(articleDAO).deleteById(a);
    }

    @Test
    void testIngestArticles(){

    }

    @BeforeEach
    private void init() {
        final Article article1 = new Article();
        article1.setId(1);
        article1.setAuthor("Samo");
        article1.setTitle("Java zivot dava");
        article1.setText("Java, tak uzasny programovaci jazyk");

        final Article article2 = new Article();
        article2.setId(2);
        article2.setAuthor("Ivan");
        article2.setTitle("Java++");
        article2.setText("Java under the hood");

        final Article article3 = new Article();
        article3.setId(3);
        article3.setAuthor("Tomas");
        article3.setTitle("AI");
        article3.setText("AI will take our jobs");

        final Comment comment11 = new Comment();
        comment11.setId_article(1);
        comment11.setId(1);
        comment11.setAuthor("Juraj");
        comment11.setText("Ok");

        final Comment comment12 = new Comment();
        comment12.setId_article(1);
        comment12.setId(2);
        comment12.setAuthor("Filip");
        comment12.setText("Fantastic");

        final Comment comment21 = new Comment();
        comment21.setId_article(2);
        comment21.setId(1);
        comment21.setAuthor("Oliver");
        comment21.setText("Super");

        article1.setComments(List.of(comment11,comment12));
        article2.setComments(List.of(comment21));
        article3.setComments(List.of());

        articles = new ArrayList<>();
        articles.addAll(List.of(article1,article2,article3));

        searchedArticles = new ArrayList<>();
        searchedArticles.addAll(List.of(article1,article2));

    }


}
