package sk.ness.academy.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

@Repository
public class AuthorHibernateDAO implements AuthorDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Author> findAll() {
    return this.sessionFactory
            .getCurrentSession()
            .createSQLQuery("select distinct a.author as name from articles a ")
        .addScalar("name", StringType.INSTANCE)
        .setResultTransformer(new AliasToBeanResultTransformer(Author.class)).list();
  }

  @Override
  public List<AuthorStats> getAuthorStats() {
    List<Author> authors = findAll();
    List<AuthorStats> authorStats = new ArrayList<>();
    for (Author a: authors) {
      AuthorStats as = new AuthorStats();
      as.setAuthorName(a.getName());
      int count = (int) this
              .sessionFactory
              .getCurrentSession()
              .createSQLQuery("select * from articles where author LIKE '"+a.getName()+"'").stream().count();
      as.setArticleCount(count);
      authorStats.add(as);
    }
    return authorStats;
  }

}

