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
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_num")
	private Integer post_num;
	
	@Column(name = "author_id")
	private String author_id;
	
	@Column(name = "course_id")
	private Integer course_id;
	
	@Column(name = "post_type")
	private Integer post_type;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@CreationTimestamp
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	
	public Post() {}
	
	public Post(Integer post_num, 
				String author_id, 
				Integer course_id, 
				Integer post_type, 
				String title, 
				String content,
				LocalDateTime timestamp) {
		this.post_num = post_num;
		this.author_id = author_id;
		this.course_id = course_id;
		this.post_type = post_type;
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
	}

	public Integer getPost_num() {
		return post_num;
	}

	public void setPost_num(Integer post_num) {
		this.post_num = post_num;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public Integer getPost_type() {
		return post_type;
	}

	public void setPost_type(Integer post_type) {
		this.post_type = post_type;
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
		return "Post [post_num=" + post_num + ", author_id=" + author_id + ", course_id=" + course_id + ", post_type="
				+ post_type + ", title=" + title + ", content=" + content + ", timestamp=" + timestamp + "]";
	}

}

