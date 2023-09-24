package com.junmo.board_project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;   // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문
    @Setter
    private String hashtag; // 해시 태그

    @ToString.Exclude
    @OrderBy("id") // ArticleComment 불러올때 정렬 기준 id로 설정
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 연관관계 주인 article 설정
    private final Set<ArticleComment> articleComment = new LinkedHashSet<>();


    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자


    // 이 엔티티 객체를 외부에서 함부로 new를 통해 만들지 못하도록 기본 생성자를 protected로 설정
    // @NoArgsConstructor(access = AccessLevel.PROTECTED) 대신의 역할을 할 수 있다. (간단한거니 그냥 만들어줌)
    protected Article() {
    }

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // new 없이 엔티티 객체 만들라고 아래와 같이 of 함수 생성 (생성자 대신 사용되는 팩토리 메서드)
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 콜렉션에 이 엔티티 객체를 넣고 비교, 분석, 정렬을 할 수 있어 해당 함수 오버라이드 해줘야한다.
    // 동등성 검사는 유니크한 id 값으로 하면되기에 id 값만으로 로직을 구성한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id); // id가 있는 엔티티이며, 같은 id여야지 true
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
