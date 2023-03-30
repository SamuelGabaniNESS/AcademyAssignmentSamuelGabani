package sk.ness.academy.exceptions;

public class CommentsNotFoundException extends RuntimeException{
    private int articleId = 0;
    private int commentId = 0;
    public CommentsNotFoundException(int articleId){
        this.articleId = articleId;
    }

    public CommentsNotFoundException(int articleId,int commentId){
        this(articleId);
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getCommentId() {
        return commentId;
    }
}
