package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.PostDTO;
import com.andrew.prophiusassessment.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO getPostById(Long id);
    long getTotalPosts();
    PostDTO updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
    Page<PostDTO> getAllPosts(Pageable pageable);
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
}
