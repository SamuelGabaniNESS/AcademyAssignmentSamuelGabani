package sk.ness.academy.exceptions;

public class ArticleIllegalArgumentException extends RuntimeException{
    private String author;
    private String title;
    private String text;

    public ArticleIllegalArgumentException(String author, String title, String text){
        this.author = author;
        this.title = title;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
