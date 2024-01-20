package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.PostDTO;
import com.andrew.prophiusassessment.entity.Post;
import com.andrew.prophiusassessment.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedDate(LocalDateTime.now());
        post.setLikesCount(0);

        Post savedPost = postRepository.save(post);
        return convertEntityToDTO(savedPost);
    }

    @Override
    public PostDTO getPostById(Long id) {
        return null;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return null;
    }

    private PostDTO convertEntityToDTO(Post post) {
        // Conversion logic
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedDate(post.getCreatedDate());
        postDTO.setLikesCount(post.getLikesCount());

        return postDTO;
    }

    private Post convertDTOToEntity(PostDTO postDTO) {
        // Conversion logic
        return null;
    }

}
