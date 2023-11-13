package oes.moduleName.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commentId")
	private Integer commentId;
	
	@Column(name = "postNum")
	private Integer postNum;
	
	@Column(name = "userId")
	private String userId;
	
    @CreationTimestamp
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	@Column(name = "message")
	private String message;

    public Comment() {}

    public Comment(Integer commentId, Integer postNum, String userId, LocalDateTime timestamp, String message) {
        this.commentId = commentId;
        this.postNum = postNum;
        this.userId = userId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostNum() {
        return postNum;
    }

    public void setPostNum(Integer postNum) {
        this.postNum = postNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", postNum=" + postNum + ", userId=" + userId + ", timestamp="
                + timestamp + ", message=" + message + "]";
    }

    
}

