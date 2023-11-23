package oes.post_service.entity;

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
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postNum")
	private Integer postNum;
	
	@Column(name = "authorId")
	private String authorId;
	
	@Column(name = "courseId")
	private Integer courseId;
	
	@Column(name = "postType")
	private Integer postType;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@CreationTimestamp
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	
	public Post() {}
	
	public Post(Integer postNum, 
				String authorId, 
				Integer courseId, 
				Integer postType, 
				String title, 
				String content,
				LocalDateTime timestamp) {
		this.postNum = postNum;
		this.authorId = authorId;
		this.courseId = courseId;
		this.postType = postType;
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
	}

	public Integer getPostNum() {
		return postNum;
	}

	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getPostType() {
		return postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "Post [postNum=" + postNum + ", authorId=" + authorId + ", courseId=" + courseId + ", postType="
				+ postType + ", title=" + title + ", content=" + content + ", timestamp=" + timestamp + "]";
	}

}

