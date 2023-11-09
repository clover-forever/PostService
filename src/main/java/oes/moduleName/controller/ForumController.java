package oes.moduleName.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import oes.moduleName.entity.Post;
import oes.moduleName.repository.PostRepository;

@RestController
public class ForumController {
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/")
	public String index() {
		return "Welcome to hell again and again";
	}
	
	@PostMapping("/addForum/{courseId}")
	public Post saveForum(@RequestBody Post post, @PathVariable int courseId) {
		post.setCourseId(courseId);
		post.setPostType(0);
		postRepository.save(post);
		return post;
	}
	
	@GetMapping("/getAllForum/{courseId}")
	public List<Post> getAllForum(@PathVariable int courseId){
		List<Post> forumList = postRepository.findBycourseId(courseId);
		return forumList;
	}
	
//	@DeleteMapping("/deleteForum/{post_num}")
//	public String deleteForum(@PathVariable int post_num) {
//		Optional<Post> delPost = postRepository.findById(post_num).get();
//		if(delPost != null) {
//			postRepository.delete(delPost);
//		}
//		else {
//			return "not found forum";
//		}
//		return "Delete Successfully";
//	}
	@DeleteMapping("/deleteForum/{postNum}")
	public ResponseEntity<String> deleteForum(@PathVariable int postNum) {
	    Optional<Post> postOptional = postRepository.findById(postNum);
	    if (postOptional.isPresent()) {
	        Post delPost = postOptional.get();
	        postRepository.delete(delPost);
	        return ResponseEntity.ok("Delete Successfully");
	    } else {
	        return ResponseEntity.ok("Forum not found.");
	    }
	}
	
	@PutMapping("/updateForum/{postNum}")
	public ResponseEntity<Post> updateForumData(@RequestBody Post updatedPost, @PathVariable int postNum) {
	    Optional<Post> postOptional = postRepository.findById(postNum);
	    if (postOptional.isPresent()) {
	        Post existingPost = postOptional.get();
	        
	        existingPost.setTitle(updatedPost.getTitle());
	        existingPost.setContent(updatedPost.getContent());
	        
	        Post savedPost = postRepository.save(existingPost);
	        
	        return ResponseEntity.ok(savedPost);
	    } 
	    else {
	        return ResponseEntity.notFound().build();
	    }
	}


}
