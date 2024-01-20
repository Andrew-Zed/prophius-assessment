package com.andrew.prophiusassessment;


import com.andrew.prophiusassessment.entity.Comment;
import com.andrew.prophiusassessment.entity.Post;
import com.andrew.prophiusassessment.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class PostTest {

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
    void updateLikesCount() {
        Post post = new Post();
        post.setLikesCount(5);
        assertEquals(5, post.getLikesCount());

        post.setLikesCount(10);
        assertEquals(10, post.getLikesCount());
    }

}
