package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO getPostById(Long id);
    List<PostDTO> getAllPosts();

}
