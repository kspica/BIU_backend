package org.api.biuquiz.specifications;

import org.api.biuquiz.models.Quiz;
import org.springframework.data.jpa.domain.Specification;

public class QuizSpecifications {

    public static Specification<Quiz> titleContains(String title) {
        return (root, query, cb) ->
                title == null || title.isEmpty() ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Quiz> categoryEquals(String category) {
        return (root, query, cb) ->
                category == null || category.isEmpty() ? null : cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }

    public static Specification<Quiz> difficultyEquals(String difficulty) {
        return (root, query, cb) ->
                difficulty == null || difficulty.isEmpty() ? null : cb.equal(cb.lower(root.get("difficulty")), difficulty.toLowerCase());
    }

    public static Specification<Quiz> timeLimitEquals(Integer timeLimit) {
        return (root, query, cb) ->
                timeLimit == null ? null : cb.equal(root.get("timeLimit"), timeLimit);
    }
}

