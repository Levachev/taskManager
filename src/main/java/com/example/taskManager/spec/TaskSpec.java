package com.example.taskManager.spec;

import com.example.taskManager.entity.Task;
import com.example.taskManager.spec.filter.TaskFilter;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpec {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String AUTHOR = "author";
    private static final String PERFORMER = "performer";
    private static final String STATUS = "status";
    private static final String PRIORITY = "priority";

    private TaskSpec() {
    }

    public static Specification<Task> filterBy(TaskFilter filter) {
        return Specification
                .where(hasAuthorId(filter.authorId()))
                .and(hasPerformerId(filter.performerId()))
                .and(containsTitle(filter.title()))
                .and(containsDescription(filter.description()))
                .and(hasPriority(filter.priority()))
                .and(hasStatus(filter.status()));
    }


    private static Specification<Task> hasAuthorId(Long id) {
        return (root, query, cb) -> id == null ? cb.conjunction() : cb.equal(root.get(AUTHOR).get("id"), id);
    }

    private static Specification<Task> hasPerformerId(Long id) {
        return (root, query, cb) -> id == null ? cb.conjunction() : cb.equal(root.get(PERFORMER).get("id"), id);
    }

    private static Specification<Task> containsTitle(String titlePart) {
        return (root, query, cb) -> titlePart == null || titlePart.isEmpty()
                ? cb.conjunction() : cb.like(cb.upper(root.get(TITLE)),
                "%"+titlePart.toUpperCase()+"%");
    }

    private static Specification<Task> containsDescription(String descriptionPart) {
        return (root, query, cb) -> descriptionPart == null || descriptionPart.isEmpty()
                ? cb.conjunction() : cb.like(cb.upper(root.get(DESCRIPTION)),
                "%"+descriptionPart.toUpperCase()+"%");
    }

    private static Specification<Task> hasStatus(String status) {
        return (root, query, cb) -> status == null || status.isEmpty()
                ? cb.conjunction() : cb.like(root.get(STATUS), status);
    }

    private static Specification<Task> hasPriority(String priority) {
        return (root, query, cb) -> priority == null || priority.isEmpty()
                ? cb.conjunction() : cb.like(root.get(PRIORITY), priority);
    }
}
