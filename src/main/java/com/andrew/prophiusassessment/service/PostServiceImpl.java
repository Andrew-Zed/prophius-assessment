package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.PostDTO;
import com.andrew.prophiusassessment.entity.Post;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.exceptions.PostNotFoundException;
import com.andrew.prophiusassessment.repositories.PostRepository;
import com.andrew.prophiusassessment.repositories.UserRepository;
import com.andrew.prophiusassessment.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        return convertEntityToDTO(post);
    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::convertEntityToDTO);
    }

    @Override
    public long getTotalPosts() {
        return postRepository.count();
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));

        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return convertEntityToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));
        postRepository.delete(post);
    }

    @Transactional
    @Override
    public void likePost(Long postId, Long userId) {
      Post post = postRepository.findById(postId)
              .orElseThrow(() -> new RuntimeException("Post not found"));
      User user = userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("User not found"));

      post.addLike(user);
      postRepository.save(post);
    }

    @Transactional
    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.removeLike(user);
        postRepository.save(post);
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
