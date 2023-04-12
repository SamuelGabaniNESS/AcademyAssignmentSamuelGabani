package sk.ness.academy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory
            .getCurrentSession()
            .createQuery("select a from Article a", Article.class)
            .getResultList();
//    return this.sessionFactory
//            .getCurrentSession()
//            .createSQLQuery("select id,title,text,author,create_timestamp from articles").list();
//    return this.sessionFactory
//            .getCurrentSession()
//            .createSQLQuery("select id,title,text,author,create_timestamp from articles")
//            .addEntity(Article.class).list();
//    return this.sessionFactory
//            .getCurrentSession()
//            .createSQLQuery("select a.id as id, a.title as title, a.text as text, a.author as author, a.create_timestamp as create_timestamp from articles a")
//            .addScalar("id", IntegerType.INSTANCE)
//            .addScalar("title", StringType.INSTANCE)
//            .addScalar("text", StringType.INSTANCE)
//            .addScalar("author", StringType.INSTANCE)
//            .addScalar("create_timestamp", DateType.INSTANCE)
//            .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  }
  @Override
  public List<Article> searchAll(String searchedText){
    return this.sessionFactory
            .getCurrentSession()
            .createSQLQuery("select * from articles " +
                    "where author like '%"+searchedText+"%' or text like '%"+searchedText+"%' or title like '%"+searchedText+"%'")
            .addEntity(Article.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteById(final Article article) {
    this.sessionFactory.getCurrentSession().delete(article);
  }

}
