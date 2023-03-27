package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment findByID(Integer commentId);

    List<Comment> findAll(Integer article_id);

    void createComment(Comment comment);

    void ingestComments(String jsonComments);

    void deleteComment(final Comment comment);
}
