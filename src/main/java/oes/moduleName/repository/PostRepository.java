package oes.moduleName.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import oes.moduleName.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findBycourseId( int courseId);
}

