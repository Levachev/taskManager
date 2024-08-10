package com.example.takManager.spec;

import com.example.takManager.entity.Comment;
import com.example.takManager.spec.filter.CommentFilter;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpec {
    private static final String TASK = "task";
    private static final String COMMENTATOR = "commentator";
    private static final String COMMENT = "comment";

    private CommentSpec() {
    }

    public static Specification<Comment> filterBy(CommentFilter filter) {
        return Specification
                .where(hasTaskId(filter.taskId()))
                .and(hasCommentatorId(filter.commentatorId()))
                .and(containsComment(filter.comment()));
    }

    private static Specification<Comment> hasCommentatorId(Long id) {
        return (root, query, cb) -> id == null ? cb.conjunction() : cb.equal(root.get(COMMENTATOR).get("id"), id);
    }

    private static Specification<Comment> hasTaskId(Long id) {
        return (root, query, cb) -> id == null ? cb.conjunction() : cb.equal(root.get(TASK).get("id"), id);
    }

    private static Specification<Comment> containsComment(String commentPart) {
        return (root, query, cb) -> commentPart == null || commentPart.isEmpty()
                ? cb.conjunction() : cb.like(cb.upper(root.get(COMMENT)),
                "%"+commentPart.toUpperCase()+"%");
    }
}
