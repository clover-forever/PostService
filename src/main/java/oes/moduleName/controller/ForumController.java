package oes.moduleName.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
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
	
	@PostMapping(value = {"/{authorId}/course/{courseId}/announcement/addAnnouncement", "/{authorId}/course/{courseId}/discussion/addDiscussion"})
	public ResponseEntity<Object> saveForum(@RequestBody Map<String, String> post, 
											@PathVariable int courseId,
											@PathVariable String authorId,
											HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		String path = request.getRequestURI();

		if(path.contains("/announcement/addAnnouncement")){
			String ret = service.saveForum(post.get("title"), post.get("content"), courseId, authorId);
			response.put("response", ret);
			return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
				.body(response);
		}
		else if(path.contains("/discussion/addDiscussion")){
			String ret = service.saveDiscussion(post.get("title"), post.get("content"), courseId, authorId);
			response.put("response", ret);
			return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
				.body(response);
		}
		else{
			response.put("response", "Unknown path");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(response);
		}
	}
	
	@GetMapping(value = {"/{authorId}/course/{courseId}/announcement", "/{authorId}/course/{courseId}/discussion"})
	public List<Post> getAllForum(@PathVariable int courseId){
		List<Post> forumList = postRepository.findBycourseId(courseId);
		return forumList;
	}
	
	@DeleteMapping(value = {"/{authorId}/course/{courseId}/announcement/{postNum}/delete", "/{authorId}/course/{courseId}/discussion/{postNum}/delete"})
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
	
	@PutMapping(value = {"/{authorId}/course/{courseId}/announcement/{postNum}/update", "/{authorId}/course/{courseId}/discussion/{postNum}/update"})
	public ResponseEntity<Object> updateForumData(@RequestBody Map<String, String> updatedPost, 
												@PathVariable int postNum,
												@PathVariable String authorId,
												@PathVariable int courseId) 
	{
		Map<String, String> response = new HashMap<>();
	    
		
			Optional<Post> fp = postRepository.findById(postNum);
			Post uppost;
			if (!fp.isPresent()) {
				response.put("response", "The post_id doesn't exit");
				return ResponseEntity.badRequest().body(response);
			} 
			else {
				uppost = fp.get();
				String ret = service.updateForum(uppost, updatedPost.get("title"), updatedPost.get("content"), courseId, authorId, postNum);
				response.put("response", ret);
    			return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
        			.body(response);
			}
		
	}
}