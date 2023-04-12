package sk.ness.academy.springboothibernate.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.dao.CommentHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.service.ArticleServiceImpl;
import sk.ness.academy.service.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = {TestDataSourceConfig.class, CommentHibernateDAO.class})
public class CommentServiceImplTest {
    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private CommentServiceImpl commentService;

    private List<Article> articles;

    @Test
    void testFindById(){
        Mockito.when(commentDAO.findByID(1)).thenReturn(articles.get(0).getComments().get(0));
        final Comment comment = commentService.findByID(1);
        Assertions.assertEquals(1,comment.getId());
        Assertions.assertEquals(1,comment.getId_article());
        Assertions.assertEquals("Juraj",comment.getAuthor());
        Assertions.assertEquals("Ok",comment.getText());
    }

    @Test
    void testFindAll(){
        Mockito.when(commentDAO.findAll(1)).thenReturn(articles.get(0).getComments());
        final List<Comment> commentList = commentService.findAll(1);
        Assertions.assertEquals(3,commentList.size());
        Assertions.assertEquals(1,commentList.get(0).getId_article());
        Assertions.assertEquals("Juraj",commentList.get(0).getAuthor());
        Assertions.assertEquals("Fantastic",commentList.get(1).getText());
    }

    @Test
    void testCreateComment(){
        Comment c1 = new Comment();
        c1.setId(1);
        c1.setId_article(1);
        c1.setAuthor("Ondrej");
        c1.setText("ach prilis tazke");
        commentDAO.persist(c1);
        Mockito.verify(commentDAO).persist(c1);
    }

    @Test
    void testDeleteComment(){
        Comment c1 = new Comment();
        c1.setId(1);
        commentDAO.deleteById(c1);
        Mockito.verify(commentDAO).deleteById(c1);
    }

    @BeforeEach
    private void init() {
        articles = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        final Article article1 = new Article();
        article1.setId(1);
        article1.setAuthor("Samo");
        article1.setTitle("Java zivot dava");
        article1.setText("Java, tak uzasny programovaci jazyk");

        final Comment comment1 = new Comment();
        comment1.setId_article(1);
        comment1.setId(1);
        comment1.setAuthor("Juraj");
        comment1.setText("Ok");

        final Comment comment2 = new Comment();
        comment2.setId_article(1);
        comment2.setId(2);
        comment2.setAuthor("Filip");
        comment2.setText("Fantastic");

        final Comment comment3 = new Comment();
        comment3.setId_article(2);
        comment3.setId(1);
        comment3.setAuthor("Oliver");
        comment3.setText("Super");

        comments.addAll(List.of(comment1,comment2,comment3));
        article1.setComments(comments);
        articles.add(article1);

    }
}
