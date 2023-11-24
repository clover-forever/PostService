package oes.post_service.controller;

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

import oes.post_service.entity.Comment;
import oes.post_service.entity.Post;
import oes.post_service.presentation.PostPresentation;
import oes.post_service.presentation.CommentPresentation;
import oes.post_service.repository.CommentRepository;
import oes.post_service.repository.PostRepository;
import oes.post_service.service.PostService;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	

	@Autowired
	private PostService service;
	
	@GetMapping("/")
	public String index() {
		return "Welcome to announcement!";
	}
	
	@PostMapping(value = {"/course/{courseId}/announcement/{authorId}/addAnnouncement"})
	public ResponseEntity<Object> saveAnnouncement(@RequestBody Map<String, String> post, 
											@PathVariable int courseId,
											@PathVariable String authorId) {
		Map<String, Object> response = new HashMap<>();
		
		String ret = service.saveAnnouncement(post.get("title"), post.get("content"), courseId, authorId);
		response.put("response", ret);
		return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
			.body(response);
	}
	
	// @GetMapping(value = {"/course/{courseId}/announcement/{authorId}"})
	// public List<Post> getAllAnnouncement(@PathVariable int courseId) {
	// 	return postRepository.findBycourseIdAndpostType(courseId, 0);
	// }

	@GetMapping(value = {"/course/{courseId}/announcement/{authorId}"})
	public List<PostPresentation> getAllAnnouncement(@PathVariable int courseId) {
		List<Post> posts = postRepository.findBycourseIdAndpostType(courseId, 0);
		return service.getAllCourseList(posts);
	}
	
	@DeleteMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}/delete"})
	public ResponseEntity<String> deleteAnnouncement(@PathVariable int postNum, @PathVariable String authorId) {
	    Optional<Post> postOptional = postRepository.findById(postNum);
	    if (postOptional.isPresent()) {
	        Post delPost = postOptional.get();
	        postRepository.delete(delPost);
	        return ResponseEntity.ok("Delete Successfully");
	    } else {
	        return ResponseEntity.ok("Announcement not found.");
	    }
	}
	
	@PutMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}/update"})
	public ResponseEntity<Object> updateAnnouncementData(@RequestBody Map<String, String> updatedPost, 
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


	@PostMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}/addComment"})
	public ResponseEntity<Object> saveComment(@RequestBody Map<String, String> comment, 
											@PathVariable int postNum,
											@PathVariable String authorId) {
		Map<String, Object> response = new HashMap<>();

		String ret = service.saveComment(comment.get("message"), postNum, authorId);
		response.put("response", ret);
		return ResponseEntity.status(ret.equals("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
			.body(response);		
	}

	@GetMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}"})
	public Map<String, Object> getAllComment(@PathVariable int courseId, @PathVariable int postNum) {
		Map<String, Object> result = new HashMap<>();
	
		Post post = postRepository.findById(postNum).get();
		System.out.println(post);
		List<Comment> comments = commentRepository.findBypostNum(postNum);
		
		PostPresentation p = service.getAllCommentOfPost(post);
		List<CommentPresentation> c = service.getAllCommentOfComments(comments);

		result.put("post", p);
		result.put("comments", c);
	
		return result;
	}

	@DeleteMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}/{commentId}/delete"})
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

	@PutMapping(value = {"/course/{courseId}/announcement/{authorId}/{postNum}/{commentId}/update"})
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