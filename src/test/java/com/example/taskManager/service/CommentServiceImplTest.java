package com.example.taskManager.service;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.entity.Comment;
import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.User;
import com.example.taskManager.mapper.CommentMapper;
import com.example.taskManager.repo.CommentRepo;
import com.example.taskManager.repo.TaskRepo;
import com.example.taskManager.spec.filter.CommentFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepo commentRepo;
    @Mock
    private TaskRepo taskRepo;
    @Spy
    @InjectMocks
    private CommentServiceImpl underTest;

    @Test
    void shouldGetByTaskId() {
        //given
        List<Comment> comments = new ArrayList<>();
        int listSize = 3;
        for(int i=0;i<listSize;i++) {
            comments.add(
                    Comment.builder()
                            .comment("")
                            .id(1L)
                            .build()
            );
        }
        //when

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(commentRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(comments));

            List<CommentDto> commentList = underTest.getByTaskId(1,
                    new CommentFilter(1L, "", 1L));
            //then

            assertEquals(listSize, commentList.size());
            verify(commentRepo).findAll(any(Specification.class), any(Pageable.class));
        }
    }

    @Test
    void shouldAddComment() {
        //given
        CommentDto commentDto = new CommentDto(1L, "");

        //when
        when(commentRepo.save(any(Comment.class))).thenReturn(Comment.builder().id(1L).build());
        when(taskRepo.getReferenceById(anyLong())).thenReturn(Task.builder().id(1L).build());
        Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();
        Long result = underTest.addComment(commentDto);

        //then
        assertEquals(result, 1L);
        verify(commentRepo).save(any(Comment.class));
    }

    @Test
    void shouldDeleteCommentsByTaskId() {

        //when
        underTest.deleteCommentsByTaskId(1L);

        //then
        verify(commentRepo).deleteByTaskId(anyLong());
    }
}