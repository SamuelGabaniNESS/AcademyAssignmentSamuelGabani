package sk.ness.academy.springboothibernate.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AuthorServiceImplTest {
    @Mock
    private AuthorDAO authorDAO;
    @InjectMocks
    private AuthorServiceImpl authorService;
    private List<Author> authors;
    private List<AuthorStats> authorStats;

    @Test
    void testFindAll(){
        Mockito.when(authorDAO.findAll()).thenReturn(authors);
        final List<Author> authorDtos = authorService.findAll();

        Assertions.assertEquals(3,authorDtos.size());
        Assertions.assertEquals("Samo",authorDtos.get(0).getName());
        Assertions.assertEquals("Tomas",authorDtos.get(1).getName());
        Assertions.assertEquals("Adam",authorDtos.get(2).getName());
    }

    @Test
    void testFindAllEmpty(){
        Mockito.when(authorDAO.findAll()).thenReturn(new ArrayList<>());
        final List<Author> authorDtos = authorService.findAll();
        Assertions.assertEquals(0,authorDtos.size());
    }

    @Test
    void testFindAllNull(){
        Mockito.when(authorDAO.findAll()).thenReturn(null);
        final List<Author> authorDtos = authorService.findAll();
        Assertions.assertNull(authorDtos);
    }

    @Test
    void testGetAuthorStats(){
        Mockito.when(authorDAO.getAuthorStats()).thenReturn(authorStats);
        final List<AuthorStats> authorStatsDtos = authorService.getAuthorStats();
        Assertions.assertEquals(3,authorStatsDtos.size());
        Assertions.assertEquals("Samo",authorStatsDtos.get(0).getAuthorName());
        Assertions.assertEquals("Tomas",authorStatsDtos.get(1).getAuthorName());
        Assertions.assertEquals("Adam",authorStatsDtos.get(2).getAuthorName());
        Assertions.assertEquals(2,authorStatsDtos.get(0).getArticleCount());
        Assertions.assertEquals(1,authorStatsDtos.get(1).getArticleCount());
        Assertions.assertEquals(3,authorStatsDtos.get(2).getArticleCount());
    }

    @Test
    void testGetAuthorStatsEmpty(){
        Mockito.when(authorDAO.getAuthorStats()).thenReturn(new ArrayList<>());
        final List<AuthorStats> authorStatsDtos = authorService.getAuthorStats();
        Assertions.assertEquals(0,authorStatsDtos.size());
    }

    @Test
    void testGetAuthorStatsNull(){
        Mockito.when(authorDAO.getAuthorStats()).thenReturn(null);
        final List<AuthorStats> authorStatsDtos = authorService.getAuthorStats();
        Assertions.assertNull(authorStatsDtos);
    }

    @BeforeEach
    private void init() {
        final Author author1 = new Author();
        author1.setName("Samo");
        final Author author2 = new Author();
        author2.setName("Tomas");
        final Author author3 = new Author();
        author3.setName("Adam");
        authors = new ArrayList<>();

        authors.add(author1);
        authors.add(author2);
        authors.add(author3);

        final AuthorStats authorStats1 = new AuthorStats();
        authorStats1.setAuthorName(author1.getName());
        authorStats1.setArticleCount(2);
        final AuthorStats authorStats2 = new AuthorStats();
        authorStats2.setAuthorName(author2.getName());
        authorStats2.setArticleCount(1);
        final AuthorStats authorStats3 = new AuthorStats();
        authorStats3.setAuthorName(author3.getName());
        authorStats3.setArticleCount(3);

        authorStats = new ArrayList<>();
        authorStats.addAll(List.of(authorStats1,authorStats2,authorStats3));
    }
}
