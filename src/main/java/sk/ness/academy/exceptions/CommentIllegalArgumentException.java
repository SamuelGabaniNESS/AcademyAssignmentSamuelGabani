package sk.ness.academy.exceptions;

public class CommentIllegalArgumentException extends RuntimeException{
    private String author;
    private String text;

    public CommentIllegalArgumentException(String author, String text){
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
