package sk.ness.academy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.ness.academy.exceptions.*;

@ControllerAdvice
public class ArticleExceptionController {
    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> exception(ArticleNotFoundException exception){
        ResponseEntity<Object> response;
        if(exception.getSearchedText() != null) {
            response = new ResponseEntity<>(
                    "Article with searched text '"+exception.getSearchedText()+"' not found.",
                    HttpStatus.NOT_FOUND);
        }else if(exception.getArticleId() != 0){
            response = new ResponseEntity<>(
                    "Article with ID: "+exception.getArticleId()+" does not exist, so it can't be deleted.",
                    HttpStatus.NOT_FOUND);
        }
        else{
            response = new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @ExceptionHandler(value = ArticleIllegalArgumentException.class)
    public ResponseEntity<Object> exception(ArticleIllegalArgumentException exception){
        String resultMessage = "Can't create article because ";
        if(exception.getAuthor() == null || exception.getText() == null || exception.getTitle() == null){
            resultMessage = resultMessage + "one or more arguments are missing in request.";
            return new ResponseEntity<>(resultMessage,HttpStatus.BAD_REQUEST);
        }else {
            if (exception.getAuthor().isEmpty()) {
                resultMessage = resultMessage + "author,";
            }
            if (exception.getTitle().isEmpty()) {
                resultMessage = resultMessage + "title,";
            }
            if (exception.getText().isEmpty()) {
                resultMessage = resultMessage + "text,";
            }
            resultMessage = resultMessage.substring(0, resultMessage.length() - 1) + " is missing.";
        }
        return new ResponseEntity<>(resultMessage,HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<Object> exception(AuthorNotFoundException exception){
        return new ResponseEntity<>("Author not found.",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CommentsNotFoundException.class)
    public ResponseEntity<Object> exception(CommentsNotFoundException exception){
        if(exception.getCommentId() != 0){
            return new ResponseEntity<>(
                    "Comment with ID: "+exception.getCommentId()+
                            " to article with ID: " + exception.getArticleId() + " not found.",
                    HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(
                    "Comments to article with ID: " + exception.getArticleId() + " not found.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(value = CommentIllegalArgumentException.class)
    public ResponseEntity<Object> exception(CommentIllegalArgumentException exception){
        String resultMessage = "Can't create comment because ";
        if(exception.getAuthor() == null || exception.getText() == null){
            resultMessage = resultMessage + "one or more arguments are missing in request.";
            return new ResponseEntity<>(resultMessage,HttpStatus.BAD_REQUEST);
        }else {
            if (exception.getAuthor().isEmpty()) {
                resultMessage = resultMessage + "author,";
            }
            if (exception.getText().isEmpty()) {
                resultMessage = resultMessage + "text,";
            }
            resultMessage = resultMessage.substring(0, resultMessage.length() - 1) + " is missing.";
        }
        return new ResponseEntity<>(resultMessage,HttpStatus.EXPECTATION_FAILED);
    }

}
