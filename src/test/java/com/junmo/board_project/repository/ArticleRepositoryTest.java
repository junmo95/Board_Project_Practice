package com.junmo.board_project.repository;

import com.junmo.board_project.config.JpaConfig;
import com.junmo.board_project.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)  // 별도로 만든 설정 클래스 추가
@DataJpaTest
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    // 생성자로 의존성을 주입하게 되면 가독성, 명시성이 올라가며
    // 런타임에 주입되는 필드 선언과 다르게 확실히 주입되고, 필드에서 final로 선언되어 불변성이 보장된다.
    public ArticleRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("sleect 테스트")
    @Test
    void givenTestData_whenSelecting_theWorksFine() {
        // Given

        // When
        // DB에서 받아온 모든 Article들을 List에 저장
        List<Article> articles = articleRepository.findAll();

        // Then
        // Articel List들은 Null 이면 안되고, 아직 DB에 더미 데이터조차도 없으니 사이즈가 0
        // 즉 현제는 가져는 와지는데 내용 개수는 0인것
        assertThat(articles)
                .isNotNull()
                .hasSize(10);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_theWorksFine() {
        // 기존 데이터 양과 데이터 추가 후 데이터 양을 비교하는 방식으로 테스트

        // Given
        long previousCount = articleRepository.count();
        Article newArticle = Article.of("new article", "new content", "#Spring");

        // When
        // DB에서 받아온 모든 Article들을 List에 저장
        Article savedArticle = articleRepository.save(newArticle);

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);

    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_theWorksFine() {
        // 데이터 하나 가져와서 수정하고 적용

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#SpringBoot";
        article.setHashtag(updatedHashtag);

        // When
        // Test 메서드 단위 별로 모든 동작이 롤백 되기 때문에 여기서 update한 내용도 롤백되어 원상복구됨
        // 그래서 그것을 고려하여 변경내용 생략 -> update 쿼리가 동작하지 않기 때문에, saveAndFlush 함수로 강제 적용
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_theWorksFine() {
        // 삭제후 데이터 수 비교
        // 연관관계 삭제 내용도 확인

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count(); // 지우기 전 전체 댓글
        int deletedArticleCommentCount = article.getArticleComment().size(); // 지워질 게시글 소속 댓글들

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedArticleCommentCount);

    }





}