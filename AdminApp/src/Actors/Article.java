package Actors;

import java.sql.Date;

public class Article {

    private int articleID;
    private String title;
    private String content;
    private String category;
    private String tags;
    private Date dateOfPublication;
    private Journalist author;

    public Article(String title, String content, Journalist author, String category) {
        this.title = title;
        this.content = content;
        this.dateOfPublication = new java.sql.Date(System.currentTimeMillis());
        this.author = author;
        this.category = category;
    }

    public Article(int articleID, String title, String content, Journalist author, String category, String tags){
        this.articleID = articleID;
        this.title = title;
        this.content = content;
        this.dateOfPublication = new java.sql.Date(System.currentTimeMillis());
        this.author = author;
        this.category = category;
        this.tags = tags;
    }

    public Article(){}

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Journalist getAuthor() {
        return author;
    }

    public void setAuthor(Journalist author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}
