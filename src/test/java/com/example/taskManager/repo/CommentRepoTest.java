package com.example.taskManager.repo;

import com.example.taskManager.entity.Comment;
import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.User;
import com.example.taskManager.model.Priority;
import com.example.taskManager.model.Role;
import com.example.taskManager.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CommentRepoTest {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo underTest;
    private Task task;
    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .email("")
                .password("")
                .role(Role.ROLE_USER)
                .build();
        user = userRepo.save(user);

        task = Task.builder()
                .title("")
                .description("")
                .status(Status.COMPLETE)
                .priority(Priority.LOW)
                .author(user)
                .build();
        task = taskRepo.save(task);
    }

    @AfterEach
    public void teardown() {
        underTest.deleteAll();
        taskRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void shouldDeleteByTaskId() {
        //given
        Comment comment = Comment.builder()
                .commentator(user)
                .task(task)
                .comment("")
                .build();
        underTest.save(comment);

        //when
        underTest.deleteByTaskId(task.getId());

        //then
        List<Comment> commentList = underTest.findAll();
        assertTrue(commentList.isEmpty());
    }
}
