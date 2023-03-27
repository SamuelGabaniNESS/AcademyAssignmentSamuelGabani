package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.dao.CommentHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    @Resource
    private CommentDAO commentDAO;

    @Override
    public Comment findByID(final Integer commentId) {
        return this.commentDAO.findByID(commentId);
    }


    public List<Comment> findAll(Integer article_id) {
        return this.commentDAO.findAll(article_id);
    }

    @Override
    public void createComment(final Comment comment) {
        this.commentDAO.persist(comment);
    }

    @Override
    public void ingestComments(final String jsonComments) {
        throw new UnsupportedOperationException("Article ingesting not implemented.");
    }

    @Override
    public void deleteComment(final Comment comment) {
        this.commentDAO.deleteById(comment);
    }
}
