package oes.moduleName.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import oes.moduleName.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findBycourseId( int courseId);
    
    @Query("SELECT p FROM Post p WHERE p.courseId = :courseId AND p.postType = :postType")
    List<Post> findBycourseIdAndpostType(int courseId, int postType);
}

