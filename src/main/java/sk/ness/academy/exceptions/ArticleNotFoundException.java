package sk.ness.academy.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    private String searchedText = null;
    private int articleId = 0;
    public ArticleNotFoundException(){

    }
    public ArticleNotFoundException(String searchedText){
        this.searchedText = searchedText;
    }

    public ArticleNotFoundException(int articleId){
        this.articleId = articleId;
    }

    public String getSearchedText() {
        return searchedText;
    }

    public int getArticleId() {
        return articleId;
    }
}
