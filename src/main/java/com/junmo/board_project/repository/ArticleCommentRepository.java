package com.junmo.board_project.repository;

import com.junmo.board_project.domain.Article;
import com.junmo.board_project.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
