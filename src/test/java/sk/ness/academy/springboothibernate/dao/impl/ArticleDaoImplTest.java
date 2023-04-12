package sk.ness.academy.springboothibernate.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.service.ArticleServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = {TestDataSourceConfig.class, ArticleHibernateDAO.class})
@Transactional
@Sql({"/testdb.sql"})
public class ArticleDaoImplTest {
    @Autowired
    private ArticleHibernateDAO articleHibernateDAO;
    private Article article;

    @BeforeEach
    public void beforeEach(){
        article = new Article();
        article.setId(4);
        article.setAuthor("Duro");
        article.setTitle("Python");
        article.setText("Python is very good language");
        Comment c1 = new Comment();
        c1.setId_article(4);
        c1.setId(1);
        c1.setText("Ok");
        c1.setAuthor("Miso");
        Comment c2 = new Comment();
        c2.setId_article(4);
        c2.setId(2);
        c2.setText("AKo mohlo to byt lepsie");
        c2.setAuthor("Misa");
        article.setComments(new ArrayList<>(List.of(c1,c2)));
    }

    @Test
    void findById(){
        final Article article = articleHibernateDAO.findByID(1);
        Assertions.assertEquals(1,article.getId());
        Assertions.assertEquals("SamuelGab",article.getAuthor());
        Assertions.assertEquals("This is example of article",article.getText());
        Assertions.assertEquals("skuska1",article.getTitle());
        Assertions.assertEquals(2,article.getComments().size());

    }

    @Test
    void findAllTest(){
        final List<Article> articles = articleHibernateDAO.findAll();
        Assertions.assertEquals(3,articles.size());
        Assertions.assertEquals(1,articles.get(0).getId());
        Assertions.assertEquals("SamuelGab",articles.get(0).getAuthor());
        Assertions.assertEquals("Article about article",articles.get(1).getText());
        Assertions.assertEquals("Java--",articles.get(2).getTitle());
    }

    @Test
    void searchAllTest() {
        final List<Article> search = articleHibernateDAO.searchAll("Java");
        Assertions.assertEquals("TomasDur", search.get(0).getAuthor());
        Assertions.assertEquals(1,search.size());
        Assertions.assertEquals("Java--",search.get(0).getTitle());
    }

    @Test
    void persistTest(){
        articleHibernateDAO.persist(article);
        Assertions.assertEquals("Duro",
                articleHibernateDAO.findByID(4).getAuthor());
        Assertions.assertEquals("Python",
                articleHibernateDAO.findByID(4).getTitle());
        Assertions.assertEquals("Miso",
                articleHibernateDAO.findByID(4).getComments().get(0).getAuthor());
        Assertions.assertEquals(2,
                articleHibernateDAO.findByID(4).getComments().size());
    }

    @Test
    void deleteArticleTest(){
        articleHibernateDAO.persist(article);
        Assertions.assertEquals(article,articleHibernateDAO.findByID(4));
        articleHibernateDAO.deleteById(article);
        Assertions.assertNull(articleHibernateDAO.findByID(4));
    }
}
