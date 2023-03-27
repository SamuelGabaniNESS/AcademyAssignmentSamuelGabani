package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {
    Comment findByID(Integer commentId);
    List<Comment> findAll(Integer article_id);

    void persist(Comment comment);
    void deleteById(final Comment comment);
}
