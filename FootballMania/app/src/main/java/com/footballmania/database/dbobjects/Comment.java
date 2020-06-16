package com.footballmania.database.dbobjects;

public class Comment {

    private int id;
    private String content;
    private long dateOfPublication;
    private int idUser;
    private int idArticle;

    public Comment(int id, String content, long dateOfPublication, int idUser, int idArticle){
        setId(id);
        setContent(content);
        setDateOfPublication(dateOfPublication);
        setIdUser(idUser);
        setIdArticle(idArticle);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(long dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
}
