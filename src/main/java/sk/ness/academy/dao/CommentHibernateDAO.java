package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO{
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Comment findByID(final Integer commentId) {
        return (Comment) this
                .sessionFactory
                .getCurrentSession()
                .get(Comment.class,commentId);
//        return (Comment) this
//                .sessionFactory
//                .getCurrentSession()
//                .createSQLQuery("select * " +
//                        "from comments " +
//                        "where id_article = "+articleId+" and id = "+commentId);
//                .get(Comment.class, commentId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> findAll(Integer article_id) {
        return this.sessionFactory
                .getCurrentSession()
                .createSQLQuery("select * from comments where id_article = "+article_id)
                .addEntity(Comment.class)
                .list();
    }

    @Override
    public void persist(final Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteById(final Comment comment) {
        this.sessionFactory.getCurrentSession().delete(comment);
    }
}
