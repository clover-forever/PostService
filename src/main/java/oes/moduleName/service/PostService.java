package oes.moduleName.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oes.moduleName.entity.Post;
import oes.moduleName.repository.PostRepository;

@Component
public class PostService {

    @Autowired
    private PostRepository postRepository;

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
}