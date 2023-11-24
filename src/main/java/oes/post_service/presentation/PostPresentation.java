package oes.post_service.presentation;

import java.time.LocalDateTime;

public class PostPresentation {
    private String time;
    private String authorName;
    private String title;
    private String content;
    
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public PostPresentation(){

    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public PostPresentation(String time, String authorName, String title, String content) {
        this.time = time;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    } 
}
