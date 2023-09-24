package com.junmo.board_project.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// 엔티티의 중복되는 부분 뽑아서 매퍼 클래스 생성
@ToString
@Getter
@EntityListeners(AuditingEntityListener.class) // auditing 리스너 설정 어노테이션(이벤트 감지, 관련 동작)
@MappedSuperclass
public class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 스프링에서 해당 시간변수 처리 시 형태 지정 (문자열 <> 시간)
    @CreatedDate
    @Column(nullable = false, updatable = false) // 수정불가 하게 옵션 추가
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length = 100, updatable = false) // 수정불가 하게 옵션 추가
    private String createdBy; // 생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자


}
