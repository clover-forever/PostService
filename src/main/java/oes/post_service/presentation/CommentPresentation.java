package oes.post_service.presentation;

public class CommentPresentation {
    private String time;
    private String message;
    private String authorName;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public CommentPresentation(String time, String message, String authorName) {
        this.time = time;
        this.message = message;
        this.authorName = authorName;
    }
    public CommentPresentation(){}
}
