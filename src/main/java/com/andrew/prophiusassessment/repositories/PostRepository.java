package com.andrew.prophiusassessment.repositories;

import com.andrew.prophiusassessment.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
