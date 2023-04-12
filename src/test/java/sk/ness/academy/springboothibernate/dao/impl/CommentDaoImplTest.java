package sk.ness.academy.springboothibernate.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.dao.CommentHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = {TestDataSourceConfig.class, CommentHibernateDAO.class})
@Transactional
@Sql({"/testdb.sql"})
public class CommentDaoImplTest {
    @Autowired
    private CommentHibernateDAO commentHibernateDAO;
    private Comment comment;

    @BeforeEach
    public void beforeEach(){
        comment = new Comment();
        comment.setId_article(1);
        comment.setId(3);
        comment.setText("Ok");
        comment.setAuthor("Miso");

    }

    @Test
    void findByIdTest(){
        final Comment comment = commentHibernateDAO.findByID(1);
        Assertions.assertEquals(1,comment.getId());
        Assertions.assertEquals("Katarina",comment.getAuthor());
        Assertions.assertEquals("celkom ok example",comment.getText());
    }

    @Test
    void findAllTest(){
        final List<Comment> comments = commentHibernateDAO.findAll(1);
        Assertions.assertEquals(2,comments.size());
        Assertions.assertEquals("Dusan",comments.get(1).getAuthor());
        Assertions.assertEquals("interesting",comments.get(1).getText());
    }

    @Test
    void persistTest(){
        commentHibernateDAO.persist(comment);
        Assertions.assertEquals("Miso",
                commentHibernateDAO.findByID(3).getAuthor());
    }

    @Test
    void deleteByIdTest(){
        commentHibernateDAO.persist(comment);
        Assertions.assertEquals("Ok",
                commentHibernateDAO.findByID(3).getText());
        commentHibernateDAO.deleteById(comment);
        Assertions.assertNull(commentHibernateDAO.findByID(3));
    }
}
