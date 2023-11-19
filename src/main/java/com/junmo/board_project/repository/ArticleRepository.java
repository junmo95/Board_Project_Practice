package com.junmo.board_project.repository;

import com.junmo.board_project.domain.Article;
import com.junmo.board_project.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        // 해당 엔티티 안에 있는 모든 필드에 대한 기본 검색기능 추가
        QuerydslPredicateExecutor<Article>,
        // 위에 것은 완벽한 일치해야만 찾아지니 나만의 설정을 하기위한 상속
        QuerydslBinderCustomizer<QArticle>
{

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // QuerydslPredicateExecutor 로 인해 모든 필드에 대한 검색이 열려있는데 원하는 대상만 적용하기 위한 옵션 off 설정
        bindings.excludeUnlistedProperties(true);
        // 검색 기능 원하는 대상만 적용
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        // 완벽일치 => 일부일치로
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
