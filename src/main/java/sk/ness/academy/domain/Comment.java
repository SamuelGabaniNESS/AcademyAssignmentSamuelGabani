package sk.ness.academy.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comments_seq_store", sequenceName = "comment_seq", allocationSize = 1)
public class Comment {
    public Comment() {
        this.createTimestamp = new Date();
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq_store")
    private Integer id;

    @Column(name = "id_article")
    private Integer id_article;

    @Column(name = "author", length = 250)
    private String author;

    @Column(name = "text", length = 2000)
    private String text;

    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    public Integer getId_article() {
        return id_article;
    }

    public void setId_article(Integer id_article) {
        this.id_article = id_article;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
}
