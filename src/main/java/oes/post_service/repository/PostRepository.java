package oes.post_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import oes.post_service.entity.Post;

import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findBycourseId( int courseId);
    
    @Query("SELECT p FROM Post p WHERE p.courseId = :courseId AND p.postType = :postType")
    List<Post> findBycourseIdAndpostType(int courseId, int postType);
}

