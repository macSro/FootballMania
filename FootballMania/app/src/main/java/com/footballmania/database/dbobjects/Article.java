package com.footballmania.database.dbobjects;

public class Article {

    private int id;
    private String title;
    private long dateOfPublication;
    private String content;
    private String journalistName;
    private String journalistEmail;
    private String category;
    private String[] tags;

    public Article(int id, String title, long dateOfPublication, String journalistFirstName, String journalistLastName, String category, String tags){
        setId(id);
        setTitle(title);
        setDateOfPublication(dateOfPublication);
        setContent(content);
        setJournalistName(journalistFirstName + " " + journalistLastName);
        setCategory(category);
        setTags(tags.split(";"));
    }

    public Article(String title, long dateOfPublication, String content, String journalistFirstName, String journalistLastName, String category, String tags){
        setTitle(title);
        setDateOfPublication(dateOfPublication);
        setContent(content);
        setJournalistName(journalistFirstName + " " + journalistLastName);
        setCategory(category);
        setTags(tags.split(";"));
    }

    public Article(int id, String title, long dateOfPublication, String content, String journalistFirstName, String journalistLastName, String category, String tags){
        setId(id);
        setTitle(title);
        setDateOfPublication(dateOfPublication);
        setContent(content);
        setJournalistName(journalistFirstName + " " + journalistLastName);
        setCategory(category);
        setTags(tags.split(";"));
    }

    public boolean isTagged(String tag){
        for(String t : tags){
            if(t.equals(tag)) return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(long dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getJournalistName() {
        return journalistName;
    }

    public void setJournalistName(String journalistName) {
        this.journalistName = journalistName;
    }

    public String getJournalistEmail() {
        return journalistEmail;
    }

    public void setJournalistEmail(String journalistEmail) {
        this.journalistEmail = journalistEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTagsToString() {
        String result = null;
        for(String s : tags){
            result=result+s+";";
        }
        return result;
    }
}
