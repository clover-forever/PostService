package oes.moduleName.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import oes.moduleName.service.PostService;

@RestController
public class ForumController {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostService service;
	
	@GetMapping("/")
	public String index() {
		return "Welcome to hell again and again";
	}
	
	@PostMapping("/{authorId}/{courseId}/addForum")
	public ResponseEntity<Object> saveForum(@RequestBody Map<String, String> post, 
											@PathVariable int courseId,
											@PathVariable String authorId) {
		Map<String, Object> response = new HashMap<>();
    	String ret = service.saveForum(post.get("title"), post.get("content"), courseId, authorId);
    	response.put("response", ret);
    	return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
        	.body(response);
	}
	
	@GetMapping("/getAllForum/{courseId}")
	public List<Post> getAllForum(@PathVariable int courseId){
		List<Post> forumList = postRepository.findBycourseId(courseId);
		return forumList;
	}
	
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