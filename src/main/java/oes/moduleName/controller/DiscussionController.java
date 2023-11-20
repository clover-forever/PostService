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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import jakarta.servlet.http.HttpServletRequest;
import oes.moduleName.entity.Post;
import oes.moduleName.entity.Comment;
import oes.moduleName.repository.CommentRepository;
import oes.moduleName.repository.PostRepository;
import oes.moduleName.service.PostService;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService service;
	
	@GetMapping("/")
	public String index() {
		return "Welcome to discussion!";
	}
	
	@PostMapping(value = {"/course/{courseId}/discussion/{authorId}/addDiscussion"})
	public ResponseEntity<Object> saveDiscussion(@RequestBody Map<String, String> post, 
											@PathVariable int courseId,
											@PathVariable String authorId) {
		Map<String, Object> response = new HashMap<>();
		
        String ret = service.saveDiscussion(post.get("title"), post.get("content"), courseId, authorId);
        response.put("response", ret);
        return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(response);
	}
	
	@GetMapping(value = {"/course/{courseId}/discussion/{authorId}"})
	public List<Post> getAllDiscussion(@PathVariable int courseId) {
		return postRepository.findBycourseIdAndpostType(courseId, 1);
	}
	
	@DeleteMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}/delete"})
	public ResponseEntity<String> deleteDiscussion(@PathVariable int postNum, @PathVariable String authorId) {
	    Optional<Post> postOptional = postRepository.findById(postNum);
	    if (postOptional.isPresent()) {
	        Post delPost = postOptional.get();
	        postRepository.delete(delPost);
	        return ResponseEntity.ok("Delete Successfully");
	    } else {
	        return ResponseEntity.ok("Discussion not found.");
	    }
	}
	
	@PutMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}/update"})
	public ResponseEntity<Object> updateDiscussionData(@RequestBody Map<String, String> updatedPost, 
												@PathVariable int postNum,
												@PathVariable String authorId) 
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
				String ret = service.updateForum(uppost, updatedPost.get("title"), updatedPost.get("content"), authorId);
				response.put("response", ret);
    			return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
        			.body(response);
			}
		
	}


	@PostMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}/addComment"})
	public ResponseEntity<Object> saveComment(@RequestBody Map<String, String> comment, 
											@PathVariable int postNum,
											@PathVariable String authorId) {
		Map<String, Object> response = new HashMap<>();

		String ret = service.saveComment(comment.get("message"), postNum, authorId);
		response.put("response", ret);
		return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
			.body(response);		
	}

	@GetMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}"})
	public List<Comment> getAllComment(@PathVariable int postNum) {
		return commentRepository.findBypostNum(postNum);
	}

	@DeleteMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}/{commentId}/delete"})
	public ResponseEntity<String> deleteComment(@PathVariable int commentId, @PathVariable String authorId) {
	    Optional<Comment> commentOptional = commentRepository.findById(commentId);
	    if (commentOptional.isPresent()) {
	        Comment delComment = commentOptional.get();
	        commentRepository.delete(delComment);
	        return ResponseEntity.ok("Delete Successfully");
	    } else {
	        return ResponseEntity.ok("Comment not found.");
	    }
	}

	@PutMapping(value = {"/course/{courseId}/discussion/{authorId}/{postNum}/{commentId}/update"})
	public ResponseEntity<Object> updateComment(@RequestBody Map<String, String> updatedComment,
												@PathVariable String authorId,
												@PathVariable int commentId) 
	{
		Map<String, String> response = new HashMap<>();
	    
		
			Optional<Comment> fc = commentRepository.findById(commentId);
			Comment upcomment;
			if (!fc.isPresent()) {
				response.put("response", "The comment_id doesn't exit");
				return ResponseEntity.badRequest().body(response);
			} 
			else {
				upcomment = fc.get();
				String ret = service.updateComment(upcomment, updatedComment.get("message"), authorId);
				response.put("response", ret);
    			return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
        			.body(response);
			}
		
	}
}
