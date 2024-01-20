package com.andrew.prophiusassessment.controller;

import com.andrew.prophiusassessment.dto.PostDTO;
import com.andrew.prophiusassessment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(Pageable pageable) {
        Page<PostDTO> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/total")
    public ResponseEntity<?> getTotalPosts() {
        long totalPosts = postService.getTotalPosts();
        return ResponseEntity.ok(totalPosts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
