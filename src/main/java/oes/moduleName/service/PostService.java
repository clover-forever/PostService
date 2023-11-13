package oes.moduleName.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oes.moduleName.entity.Post;
import oes.moduleName.entity.Comment;
import oes.moduleName.repository.PostRepository;
import oes.moduleName.repository.CommentRepository;

@Component
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String saveForum(String title, String content, Integer courseId, String authorId){
        try{
            Post post = new Post();
            post.setAuthorId(authorId);
            post.setContent(content);
            post.setTitle(title);
            post.setCourseId(courseId);
            post.setPostType(0);
            postRepository.save(post);
            return "successful";
        }
        catch (Exception e){
            return "failure";
        }

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

    public String updateForum(Post updatedPost, String title, String content, 
                                Integer courseId, String authorId, 
                                Integer postNum){
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
                                String authorId, 
                                Integer postNum,
                                Integer commentId){
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