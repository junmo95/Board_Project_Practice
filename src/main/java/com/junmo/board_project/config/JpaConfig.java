package com.junmo.board_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

// Jpa 설정
// Auditing(감사) 관련 설정 (생성/수정 시간, 자)

// 1. @Configuration
// Spring 애플리케이션의 구성 클래스임을 나타냅

// 2. @EnableJpaAuditing
// Spring Data JPA에서 제공하는 자동 감사(auditing) 기능을 활성화
// 엔티티 객체가 생성되거나 수정될 때 자동으로 관련 정보를 기록

// 3. AuditorAware
// 감사 관련 정보를 제공하는 인터페이스 현재 사용자에 대한 지정하는 인터페이스이다.
// 이 인터페이스 객체를 Bean에 등록하여 Spring(Jpa 모듈)이 가져다 쓰게 하는것

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // 아직 Security 설정이 안되서 데이터 관련 대상자 정보 획득 불가 그것을 대신할 관련설정
    // TODO : Security 미설정에 의한 임시 사용자 지정 (Auditing)
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("junmo");
    }

}




















