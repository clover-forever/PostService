package oes.post_service.service;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.converters.Auto;

import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;

import oes.post_service.entity.Comment;
import oes.post_service.entity.Post;
import oes.post_service.repository.CommentRepository;
import oes.post_service.repository.PostRepository;
import oes.post_service.presentation.CommentPresentation;
import oes.post_service.presentation.PostPresentation;
import java.time.LocalDateTime;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@Component
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    
    private PostPresentation presentation;

    // @Autowired
	// private PostPresentation postpresentation;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Adjust the pattern as needed

    private ServiceInstance getService(String requestService) {
        String serviceId = discoveryClient.getServices().stream()
                .filter(id -> id.contains(requestService)).findFirst().orElse("");
        return serviceId.isEmpty() ? null
                : discoveryClient.getInstances(serviceId).stream().findFirst().orElse(null);
    }

    public String saveAnnouncement(String title, String content, Integer courseId, String authorId){
        try{
            Post post = new Post();
            post.setAuthorId(authorId);
            post.setContent(content);
            post.setTitle(title);
            post.setCourseId(courseId);
            post.setPostType(0);
            postRepository.save(post);
            
            ServiceInstance notificationService = getService("notification");
            ServiceInstance manage_courseService = getService("manage-course");
            Map<String, Object> requestBody = new HashMap<>();

            // Format the LocalDateTime
            LocalDateTime now = LocalDateTime.now();
            
            String formattedDateTime = now.format(formatter);
            
            String manage_courseRequestUri = manage_courseService.getUri() + String.format("/ManageCourse/getCourseName/" + String.valueOf(courseId));
            ResponseEntity<Map<String, Object>> courseresponse = restTemplate.exchange(
                manage_courseRequestUri, 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = courseresponse.getBody();
            String courseName = (String) responseBody.get("response");  


            //String content = "Announcement from " + courseName + " released at " + formattedDateTime + ", Title = " + title;
            String Content = "Announcement from "+ courseName + " released at " + formattedDateTime + ", Title = " + title;
            requestBody.put("content", Content);
            requestBody.put("userId", authorId);

            String notificationRequestUri = notificationService.getUri() + String.format("/notification/system");
            
            try {
                restTemplate
                    .postForEntity(notificationRequestUri, requestBody, Object.class, Collections.emptyList());

                
                    // .getStatusCode() == HttpStatus.OK;
            } catch (Exception e) {
                System.out.println(e);
            }

            return "successful";
        }
        catch (Exception e){
            return "failure";
        }

    }

    public List<PostPresentation> getAllCourseList(List<Post> posts){ ////// need to test
        List<PostPresentation> ret = new ArrayList<>();
        for(Post p: posts){


            ServiceInstance accountService = getService("account"); 
            String accountRequestUri = accountService.getUri() + String.format("/account/getUserName/" + p.getAuthorId());
            ResponseEntity<Map<String, Object>> accountresponse = restTemplate.exchange(
                accountRequestUri, 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = accountresponse.getBody();
            String userName = (String) responseBody.get("response");  


            PostPresentation postpresentation = new PostPresentation();
            postpresentation.setAuthorName(userName);

            String formattedDateTime = p.getTimestamp().format(formatter);
            postpresentation.setTime(formattedDateTime);

            postpresentation.setTitle(p.getTitle());
            ret.add(postpresentation);
        }
        
        return ret;
    }

    public PostPresentation getAllCommentOfPost(Post p){
        ServiceInstance accountService = getService("account"); 
        String accountRequestUri = accountService.getUri() + String.format("/account/getUserName/" + p.getAuthorId());
        ResponseEntity<Map<String, Object>> accountresponse = restTemplate.exchange(
            accountRequestUri, 
            HttpMethod.GET, 
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = accountresponse.getBody();
        String userName = (String) responseBody.get("response");  

        PostPresentation postpresentation = new PostPresentation();
        postpresentation.setAuthorName(userName);

        String formattedDateTime = p.getTimestamp().format(formatter);
        postpresentation.setTime(formattedDateTime);

        postpresentation.setTitle(p.getTitle());
        postpresentation.setContent(p.getContent());
        return postpresentation;
    }

    public List<CommentPresentation> getAllCommentOfComments(List<Comment> comments){
        List<CommentPresentation> ret = new ArrayList<>();
        for(Comment c: comments){
            ServiceInstance accountService = getService("account"); 
            String accountRequestUri = accountService.getUri() + String.format("/account/getUserName/" + c.getUserId());
            ResponseEntity<Map<String, Object>> accountresponse = restTemplate.exchange(
                accountRequestUri, 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = accountresponse.getBody();
            String userName = (String) responseBody.get("response");  

            CommentPresentation commentpresentation = new CommentPresentation();
            commentpresentation.setAuthorName(userName);

            String formattedDateTime = c.getTimestamp().format(formatter);
            commentpresentation.setTime(formattedDateTime);

            commentpresentation.setMessage(c.getMessage());
        
            ret.add(commentpresentation);
        }
        return ret;
    }
    
    public String saveDiscussion(String title, String content, Integer courseId, String authorId){
        try{
            Post post = new Post();
            post.setAuthorId(authorId);
            post.setContent(content);
            post.setTitle(title);
            post.setCourseId(courseId);
            post.setPostType(1);
            postRepository.save(post);
            return "successful";
        }
        catch (Exception e){
            return "failure";
        }

    }

    public String updateForum(Post updatedPost, String title, String content, String authorId){
        try{
            updatedPost.setContent(content);
            updatedPost.setTitle(title);
            postRepository.save(updatedPost);
            return "successful";
        }
        catch (Exception e){
            return "failure";
        }
    }

    public String saveComment(String message, Integer postNum, String authorId){
        try{
            Comment comment = new Comment();
            comment.setUserId(authorId);
            comment.setMessage(message);
            comment.setPostNum(postNum);
            commentRepository.save(comment);
            return "successful";
        }
        catch (Exception e){
            return "failure";
        }

    }

    public String updateComment(Comment updatedComment, String message, 
                                String authorId){
        try{
            updatedComment.setMessage(message);
            commentRepository.save(updatedComment);
            return "successful";
        }
        catch (Exception e){
            return "failure";
        }
    }
}