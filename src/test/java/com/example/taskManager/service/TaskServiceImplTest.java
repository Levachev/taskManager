package com.example.taskManager.service;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.dto.InputTaskDto;
import com.example.taskManager.dto.TaskDto;
import com.example.taskManager.entity.Comment;
import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.User;
import com.example.taskManager.exception.UnauthorizedAccessException;
import com.example.taskManager.mapper.CommentMapper;
import com.example.taskManager.model.Priority;
import com.example.taskManager.model.Status;
import com.example.taskManager.repo.TaskRepo;
import com.example.taskManager.repo.UserRepo;
import com.example.taskManager.spec.filter.TaskFilter;
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
class TaskServiceImplTest {
    @Mock
    private TaskRepo taskRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private CommentServiceImpl commentService;
    @Spy
    @InjectMocks
    private TaskServiceImpl underTest;

    @Test
    void shouldGetAllTasksByFilter() {
        //given
        List<Task> taskList = new ArrayList<>();
        int listSize = 3;
        for(int i=0;i<listSize;i++) {
            taskList.add(
                    Task.builder()
                            .id(1L)
                            .author(User.builder().build())
                            .priority(Priority.LOW)
                            .performer(User.builder().build())
                            .status(Status.COMPLETE)
                            .title("")
                            .description("")
                            .build()
            );
        }
        //when

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(taskRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(taskList));

            List<TaskDto> taskDtos = underTest.getAllTasksByFilter(1,
                    new TaskFilter(1L, "", "", "", "", 1L));
            //then

            assertEquals(listSize, taskDtos.size());
            verify(taskRepo).findAll(any(Specification.class), any(Pageable.class));
        }
    }

    @Test
    void shouldGetTaskById() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);

            TaskDto taskDto = underTest.getTaskById(1L);
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).getReferenceById(anyLong());
        }
    }

    @Test
    void shouldCreateTask() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(userRepo.getReferenceById(anyLong())).thenReturn(User.builder().build());
            when(taskRepo.save(any(Task.class))).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            TaskDto taskDto = underTest.createTask(new InputTaskDto(
                    "", "", "LOW", "PENDING", 1L));
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).save(any(Task.class));
        }
    }

    @Test
    void shouldUpdateTaskById() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(1L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(userRepo.getReferenceById(anyLong())).thenReturn(User.builder().build());
            when(taskRepo.save(any(Task.class))).thenReturn(task);
            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            TaskDto taskDto = underTest.updateTaskById(1L, new InputTaskDto(
                    "", "", "LOW", "PENDING", 1L));
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).save(any(Task.class));
            verify(taskRepo).getReferenceById(anyLong());
        }
    }

    @Test
    void shouldNotUpdateTaskById() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(2L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        //when
        when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
        Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

        //then
        assertThrows(UnauthorizedAccessException.class, ()-> underTest.updateTaskById(1L, new InputTaskDto(
                "", "", "LOW", "PENDING", 1L)));

    }

    @Test
    void shouldDeleteTaskById() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(1L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        //when
        when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
        Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

        underTest.deleteTaskById(1L);
        //then

        verify(taskRepo).getReferenceById(anyLong());
        verify(taskRepo).deleteById(anyLong());
        verify(commentService).deleteCommentsByTaskId(anyLong());
    }

    @Test
    void shouldNotDeleteTaskById() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(2L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        //when
        when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
        Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

        //then
        assertThrows(UnauthorizedAccessException.class, ()-> underTest.deleteTaskById(1L));
    }

    @Test
    void shouldSetPerformer() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(1L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(userRepo.getReferenceById(anyLong())).thenReturn(User.builder().build());
            when(taskRepo.save(any(Task.class))).thenReturn(task);
            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            TaskDto taskDto = underTest.setPerformer(1L, 1L);
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).save(any(Task.class));
            verify(taskRepo).getReferenceById(anyLong());
        }
    }

    @Test
    void shouldNotSetPerformer() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(2L).build())
                .priority(Priority.LOW)
                .performer(User.builder().build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
        Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

        //then

        assertThrows(UnauthorizedAccessException.class, ()-> underTest.setPerformer(1L, 1L));
    }

    @Test
    void shouldSetStatus() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(1L).build())
                .priority(Priority.LOW)
                .performer(User.builder().id(1L).build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(taskRepo.save(any(Task.class))).thenReturn(task);
            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            TaskDto taskDto = underTest.setStatus(1L, "PENDING");
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).save(any(Task.class));
            verify(taskRepo).getReferenceById(anyLong());
        }
    }

    @Test
    void shouldSetStatus2() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(2L).build())
                .priority(Priority.LOW)
                .performer(User.builder().id(1L).build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

        try (MockedStatic<CommentMapper> dummy = Mockito.mockStatic(CommentMapper.class)) {
            dummy.when(() -> CommentMapper.toCommentDto(any(Comment.class)))
                    .thenReturn(new CommentDto());
            when(taskRepo.save(any(Task.class))).thenReturn(task);
            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            TaskDto taskDto = underTest.setStatus(1L, "PENDING");
            //then

            assertEquals(taskDto.getId(), task.getId());
            verify(taskRepo).save(any(Task.class));
            verify(taskRepo).getReferenceById(anyLong());
        }
    }

    @Test
    void shouldNotSetStatusByAuthor() {
        //given
        Task task = Task.builder().id(1L)
                .author(User.builder().id(2L).build())
                .priority(Priority.LOW)
                .performer(User.builder().id(2L).build())
                .status(Status.COMPLETE)
                .title("")
                .description("")
                .build();

            when(taskRepo.getReferenceById(anyLong())).thenReturn(task);
            Mockito.doReturn(User.builder().id(1L).build()).when(underTest).getCurrentUser();

            //then

            assertThrows(UnauthorizedAccessException.class, ()-> underTest.setStatus(1L, "PENDING"));

    }
}