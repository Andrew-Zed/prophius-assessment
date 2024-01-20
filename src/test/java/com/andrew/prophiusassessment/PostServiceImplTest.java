package com.andrew.prophiusassessment;


import com.andrew.prophiusassessment.dto.PostDTO;
import com.andrew.prophiusassessment.entity.Comment;
import com.andrew.prophiusassessment.entity.Post;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.repositories.PostRepository;
import com.andrew.prophiusassessment.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPostWithNoArguments() {
        Post post = new Post();
        assertNull(post.getId());
        assertNull(post.getContent());
        assertNull(post.getCreatedDate());
        assertEquals(0, post.getLikesCount());
        assertNull(post.getUser());
        assertNull(post.getComments());
    }

    @Test
    void createPostWithArguments() {
        User user = new User();
        Comment comment = new Comment();
        List<Comment> comments = Collections.singletonList(comment);

        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setContent("Test Content");
        post.setCreatedDate(now);
        post.setLikesCount(10);
        post.setUser(user);
        post.setComments(comments);

        assertEquals("Test Content", post.getContent());
        assertEquals(now, post.getCreatedDate());
        assertEquals(10, post.getLikesCount());
        assertEquals(user, post.getUser());
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));
    }

    @Test
    void getPostByIdTest() {
        Long postId = 1L;
        Post mockPost = new Post();
        mockPost.setId(postId);
        mockPost.setContent("Test Content");
        mockPost.setCreatedDate(LocalDateTime.now());
        mockPost.setLikesCount(10);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        PostDTO result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(mockPost.getContent(), result.getContent());
        assertEquals(mockPost.getLikesCount(), result.getLikesCount());

        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getAllPostsTest() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("Test Content 1");
        post1.setCreatedDate(LocalDateTime.now());
        post1.setLikesCount(5);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("Test Content 2");
        post2.setCreatedDate(LocalDateTime.now());
        post2.setLikesCount(10);

        Page<Post> page = new PageImpl<>(Arrays.asList(post1, post2));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        when(postRepository.findAll(pageable)).thenReturn(page);

        Page<PostDTO> resultPage = postService.getAllPosts(pageable);

        assertNotNull(resultPage);
        assertEquals(2, resultPage.getContent().size());
        assertEquals("Test Content 1", resultPage.getContent().get(0).getContent());
        assertEquals("Test Content 2", resultPage.getContent().get(1).getContent());

        verify(postRepository, times(1)).findAll(pageable);

    }

    @Test
    void deletePostWhenPostExists() {
        Long postId = 1L;
        Post mockPost = new Post();
        mockPost.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        postService.deletePost(postId);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(mockPost);
    }

    @Test
    void deletePostWhenPostDoesNotExist() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postService.deletePost(postId));

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    void updateLikesCount() {
        Post post = new Post();
        post.setLikesCount(5);
        assertEquals(5, post.getLikesCount());

        post.setLikesCount(10);
        assertEquals(10, post.getLikesCount());
    }

}
